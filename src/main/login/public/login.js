// GitHub OAuth 정보
const clientId = 'Ov23lijtJN9XCkxVtJT2';
const redirectUri = 'http://49.165.181.99:3000/callback';

// GitHub 로그인
document.getElementById('github-login-btn').addEventListener('click', () => {
    console.log('GitHub login button clicked');
    const githubAuthUrl = `https://github.com/login/oauth/authorize?client_id=${clientId}&redirect_uri=${encodeURIComponent(redirectUri)}&scope=user`;
    console.log('Redirecting to GitHub OAuth URL:', githubAuthUrl);
    window.location.href = githubAuthUrl;
});

const code = new URLSearchParams(window.location.search).get('code');
if (code) {
    console.log('Authorization code received:', code);
    fetchAccessToken(code);
}

async function fetchAccessToken(code) {
    console.log('Fetching access token with code:', code);
    try {
        const response = await fetch('/get-github-token', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ code })
        });
        console.log('Access token response received');
        const data = await response.json();
        console.log('Access token data:', data);

        fetchGitHubUserData(data.access_token);
    } catch (error) {
        console.error('Error fetching access token:', error);
    }
}

async function fetchGitHubUserData(token) {
    console.log('Fetching GitHub user data with token:', token);
    try {
        const response = await fetch('https://api.github.com/user', {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });
        const userData = await response.json();
        console.log('GitHub User Data:', userData);
    } catch (error) {
        console.error('Error fetching user data:', error);
    }
}

// Google OAuth 정보
const googleClientId = 'YO';

function initializeGoogleSignIn() {
    google.accounts.id.initialize({
        client_id: googleClientId,
        callback: handleGoogleCredentialResponse
    });

    google.accounts.id.renderButton(
        document.getElementById('google-login-btn'),
        { theme: 'outline', size: 'large' }
    );
}

function handleGoogleCredentialResponse(response) {
    console.log('Google credential response received:', response);
    fetch('/get-google-token', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ token: response.credential })
    })
    .then(res => res.json())
    .then(data => {
        console.log('Google User Data:', data);
    })
    .catch(error => {
        console.error('Error fetching Google token:', error);
    });
}

window.onload = initializeGoogleSignIn;
