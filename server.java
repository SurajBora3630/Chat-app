import java.net.*;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
class server extends JFrame{
    ServerSocket svrsct;
    Socket sct;
    BufferedReader br;  //for reading data from client socket
    PrintWriter out;   // to send data to client
    
      // declare components
      private JLabel label1=new JLabel("SERVER  AREA");
      private JTextArea textarea=new JTextArea();
      private JTextField messageInput=new JTextField();
      private Font font=new Font("Roboto",Font.PLAIN,20);



    //constructor
    public server()
    {
        try {
           svrsct= new ServerSocket(780);
           System.out.println("server is ready to accept connections  ");
           System.out.println("waiting");

           //////yaha tak ye socket ka wait krega  .......
           sct=svrsct.accept();  // socket request iss object mei aayegi

           br=new BufferedReader(new InputStreamReader(sct.getInputStream())); // storing message into br
            out=new PrintWriter(sct.getOutputStream());      
            startReading();
// startWriting();  
creategui();
handleEvents();  
        }
         catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void startReading() 
    {
          Runnable r1=()->{                                    // we have created a thread t1 from Runnable interface and used lambda
            System.out.println("starting reading");       
            try {
                while(true) 
                {  
                    String msg=br.readLine();      // reading message from the br object
                    if(msg.equals("exit"))
                    {
                        System.out.println("client left the chat");
                        sct.close();
                        break;
                    }
                    // System.out.println("CLIENT :"+msg);
                    textarea.append("CLIENT : "+msg+"\n");
                }

            } catch (Exception e) {
                System.out.println(e);
            }
           

          };
          new Thread(r1).start();  //thread r1 is started
    }
    private void creategui()
    {
        this.setTitle("SERVER MESSENGER");
        this.setVisible(true);
        this.setSize(600,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        textarea.setEditable(false);
        //layout set kro
        this.setLayout(new BorderLayout());
    
        // set components
        label1.setFont(font);
        textarea.setFont(font);
        messageInput.setFont(font);
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        // adding components
    
        this.add(label1,BorderLayout.NORTH);
    
    
        this.add(textarea,BorderLayout.CENTER);
        this.add(messageInput,BorderLayout.SOUTH);
    }
    private void handleEvents() {
        messageInput.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {
               
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
               
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
               
                if(e.getKeyCode()==10)
                {
                   String contentToSend=messageInput.getText(); 
                   textarea.append("ME: "+contentToSend+"\n");
                   out.println(contentToSend);
                   out.flush();
                   messageInput.setText(" ");
                   messageInput.requestFocus();
                }
            }
            
        }
        );}

    
//     public void startWriting()
//     {

//         Runnable r2=()->{    // created thread 2 for writing
//             System.out.println("writer started ...");
//             try {
//                 while(!sct.isClosed())       // that client has not exited
//                 {
//                 BufferedReader br1=new BufferedReader(new InputStreamReader(System.in)); // writing 
//                 String content=br1.readLine();
//                 out.println("ME : "+content); // printing the written content on console
//                 out.flush();
//                 if(content.equals("exit"))
//                 {
//                     sct.close();
//                     break;
//                 }
//                 }
//             } catch (Exception e) {
//                 System.out.println(e);
//             }

        
//     };
// new Thread(r2).start();
// }
    public static void main(String[] args)
    {
  new server();
    }
}