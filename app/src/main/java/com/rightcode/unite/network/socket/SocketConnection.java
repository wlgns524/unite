package com.rightcode.unite.network.socket;

import com.rightcode.unite.Util.Log;
import com.rightcode.unite.network.LegacyNetworkManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketConnection {

    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    private static SocketConnection sInstance;
    private static Socket mSocket;

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    private Emitter.Listener onConnect;
    private Emitter.Listener onDisconnect;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public static SocketConnection getInstance() {
        if (sInstance == null) {
            synchronized (SocketConnection.class) {
                sInstance = new SocketConnection();
                IO.Options opts = new IO.Options();
                opts.path = "/socket.io";
                opts.forceNew = true;
                opts.upgrade = false;
                String[] transports = new String[]{"websocket"};
                opts.transports = transports;
                try {
                    mSocket = IO.socket(LegacyNetworkManager.COM_REAL_DOMAIN, opts);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return sInstance;
    }

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------

    public void connect() {
        if (mSocket != null && mSocket.connected()) {
            Log.d("Socket is already connected");
            return;
        }

        try {
            mSocket.connect();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Socket Connecting is fail");
        }
    }

    public SocketConnection on() {
        try {
            if (onConnect != null) {
                mSocket.on(Socket.EVENT_CONNECT, onConnect);
            }
            if (onDisconnect != null) {
                mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
            }
        } catch (Exception e) {
            Log.e(e.getMessage());
        }

        return this;
    }

    public void add(SocketEventEnums.SocketEvent event, Emitter.Listener listener) {
        mSocket.on(event.toString(), listener);
    }

    public void emit(SocketEventEnums.SocketEvent event, JSONObject value) {
        mSocket.emit(event.toString(), value);
    }

    public void emit(SocketEventEnums.SocketEvent event, JSONObject value, Ack ack) {
        mSocket.emit(event.toString(), value, ack);
    }

    public static boolean isConnected() {
        if (mSocket == null) {
            return false;
        }

        if (mSocket.connected()) {
            return true;
        } else {
            return false;
        }
    }

    public SocketConnection setOnConnect(Emitter.Listener onConnect) {
        this.onConnect = onConnect;
        return this;
    }

    public SocketConnection addEvent(SocketEventEnums.SocketEvent event, Emitter.Listener listener) {
        mSocket.on(event.toString(), listener);
        return this;
    }

    public SocketConnection setOnDisconnect(Emitter.Listener onDisconnect) {
        this.onDisconnect = onDisconnect;
        return this;
    }

//    public void login(String token) {
//        Map<String, String> map = new HashMap<>();
//        map.put("token", token);
//        this.mSocket.emit("login", map);
//    }

    //----------------------------------------------------------------------------------------------
    // protected
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
}
