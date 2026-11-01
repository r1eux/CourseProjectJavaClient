package SocketAndGetService;

import Entities.Role;
import Entities.Sex;
import Entities.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket {
    private static final ClientSocket SINGLE_INSTANCE = new ClientSocket();
    private User user;
    private Role role;
    private Sex sex;
    private static Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ClientSocket() {
        try {
            socket = new Socket("localhost", 5556);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
        } catch (Exception e) {
        }
    }


    public static ClientSocket getInstance() {
        return SINGLE_INSTANCE;
    }



    public Socket getSocket() {
        return socket;
    }


    public BufferedReader getInStream() {
        return in;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    public Role getRole() {
        return role;
    }
    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }
    public void setSex(Sex sex){
        this.sex=sex;
    }
    public Sex getSex(){
        return this.sex;
    }
}
