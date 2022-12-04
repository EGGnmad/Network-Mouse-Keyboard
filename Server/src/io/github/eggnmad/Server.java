package io.github.eggnmad;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.sql.Array;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

public class Server {

    public static void main(String[] args) throws IOException{
        Server socketServer = new Server();
        socketServer.run();
    }

    public void run(){
        try {
            Robot robot = new Robot();
            int port = 8888;
            ServerSocket server = new ServerSocket(port);

            while(true){
                System.out.println("접속 대기중...");
                Socket socket = server.accept();         // 계속 기다리고 있다가 클라이언트가 접속하면 통신할 수 있는 소켓 반환
                System.out.println(socket.getInetAddress() + "로 부터 연결요청이 들어옴");
                InputStream is = socket.getInputStream();
                OutputStream os = socket.getOutputStream();
                byte[] bytes = new byte[1024];
                while (true){
                    int readByteCount = is.read(bytes);

                    os.write( "Hello".getBytes(StandardCharsets.UTF_8) );
                    os.flush();


                    if(readByteCount > 0) {
                        try{
                            String[] data = new String(bytes, 0, readByteCount, StandardCharsets.UTF_8).split(",");
                            System.out.println(Arrays.toString(data));

                            // KEYBOARD EVENT
                            if(data[0].equals("KEY")){
                                Field f = KeyEvent.class.getField( "VK_" + data[1].toUpperCase());

                                int KeyCode = f.getInt(null);

                                if(data[2].equals("False"))
                                    robot.keyPress(KeyCode);

                                else if(data[2].equals("True"))
                                    robot.keyRelease(KeyCode);
                            }

                            // MOUSE OVER EVENT
                            else if(data[0].equals("MOUSE")){
                                robot.mouseMove(Integer.parseInt(data[1]), Integer.parseInt(data[2]));
                            }
                        }

                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    else if(readByteCount == -1){
                        System.out.println(socket.getInetAddress() + "의 연결이 종료됨");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}