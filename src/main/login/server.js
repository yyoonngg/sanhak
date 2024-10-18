const express = require('express');
const axios = require('axios');
const path = require('path');
const cors = require('cors');
const mongoose = require('mongoose');
const app = express();
const port = 3000;

app.use(cors());
app.use(express.json());

// MongoDB 연결 설정
mongoose.connect('mongodb://localhost:27017/oauth_tokens', {
    useNewUrlParser: true,
    useUnifiedTopology: true
}).catch(error => {
    console.error('MongoDB 연결 오류:', error);
    process.exit(1);
});

const TokenSchema = new mongoose.Schema({
    provider: String,
    accessToken: String,
    createdAt: {
        type: Date,
        default: Date.now
    }
});

const Token = mongoose.model('Token', TokenSchema);

// 'public' 디렉토리에서 정적 파일 제공
app.use(express.static('public'));

app.post('/get-github-token', async (req, res) => {
    const { code } = req.body;
    const clientId = 'Ov23lijtJN9XCkxVtJT2';
    const clientSecret = process.env.GITHUB_CLIENT_SECRET;

    if (!code) {
        return res.status(400).json({ error: 'Authorization code is required' });
    }

    try {
        const response = await axios.post(
            'https://github.com/login/oauth/access_token',
            {
                client_id: clientId,
                client_secret: clientSecret,
                code: code
            },
            {
                headers: { 'Accept': 'application/json' }
            }
        );

        const accessToken = response.data.access_token;

        if (!accessToken) {
            return res.status(500).json({ error: 'Failed to retrieve access token from GitHub' });
        }

        const token = new Token({
            provider: 'github',
            accessToken: accessToken
        });
        await token.save();

        res.json({ access_token: accessToken });
    } catch (error) {
        if (error.response) {
            console.error('GitHub로부터 받은 오류 응답:', error.response.data);
        } else {
            console.error('응답 없이 발생한 오류:', error);
        }
        res.status(500).json({ error: 'GitHub 토큰을 가져오지 못했습니다' });
    }
});

app.post('/get-google-token', async (req, res) => {
    const { token } = req.body;

    if (!token) {
        return res.status(400).json({ error: 'ID token is required' });
    }

    try {
        const response = await axios.get(
            `https://oauth2.googleapis.com/tokeninfo?id_token=${token}`
        );
        res.json(response.data);
    } catch (error) {
        if (error.response) {
            console.error('Google로부터 받은 오류 응답:', error.response.data);
        } else {
            console.error('응답 없이 발생한 오류:', error);
        }
        res.status(500).json({ error: 'Google 토큰을 확인하지 못했습니다' });
    }
});

app.get('*', (req, res) => {
    res.sendFile(path.join(__dirname, 'public', 'index.html'));
});

app.listen(port, () => {
    console.log(`Server running on http://localhost:${port}`);
});
