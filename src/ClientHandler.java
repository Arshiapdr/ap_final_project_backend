import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler extends Thread {
    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
    }

    void writer(String write) {
        if ( !write.isEmpty()) {
            try {
                dos.writeUTF(write);
                System.out.println("write: " + write);
            } catch (IOException e) {
                try {
                    dis.close();
                    dos.close();
                    socket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                e.printStackTrace();
            }
            return;
        }
        System.out.println("invalid write");
    }


    @Override
    public void run() {
        System.out.println("Server-ready");
        String command = "";

        try {
            command = dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("command is: " + command);
        String[] split = command.split("-");
        HashMap<String, String> data;
        User clientAccount;

        switch (split[0]) {
            case "Login":
                data = new HashMap<>(
                        Map.of("username", split[1], "password", split[2]));
                clientAccount = new User(data);
                writer(clientAccount.logIn());
                break;

            case "SignUp":
                data = new HashMap<>(
                        Map.of("email", split[1], "username", split[2], "password", split[3]));
                clientAccount = new User(data);
                writer(clientAccount.signUp());
                break;
        }
    }
}



