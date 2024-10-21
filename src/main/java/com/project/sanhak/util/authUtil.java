package com.project.sanhak.util;

import java.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

public class authUtil {
    public static int getToken(String token) throws JSONException {
        String jwtToken = token.substring(7);
        String[] tokenParts = jwtToken.split("\\.");
        String payload = new String(Base64.getDecoder().decode(tokenParts[1]));
        JSONObject jsonObject = new JSONObject(payload);
        return jsonObject.getInt("uid");
    }
}
