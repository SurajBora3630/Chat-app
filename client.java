import java.net.*;

import javax.swing.BorderFactory;
import javax.swing.*;




import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
public class client extends JFrame  {
    
     Socket socket;
        BufferedReader br; 
        //for reading data from client socket
        PrintWriter out; 
      // to send data to client


      // declare components OF GUI
      private JLabel label1=new JLabel("CLIENT  AREA");
      private JTextArea textarea=new JTextArea();
      private JTextField messageInput=new JTextField();
      private Font font=new Font("Roboto",Font.PLAIN,20);
    //   private JButton send=new JButton("SEND");

      //constructor of client
    public client(){
        
        try {

System.out.println("sending request to server......");
socket=new Socket("127.0.0.1",780);
System.out.println("connection done");
br=new BufferedReader(new InputStreamReader(socket.getInputStream())); 
out=new PrintWriter(socket.getOutputStream()); 
startReading();
creategui();
handleEvents();
// buttonAction();


//  startWriting();

            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void startReading() 
    {
        
          Runnable r1=()->{
            System.out.println("starting reading");
            try {
                while(true) 
                {  
                    
                    String msg=br.readLine();
                    if(msg.equals("exit"))
                    {
                        
                        JOptionPane.showMessageDialog(this, "server terminated the chat");
                        messageInput.setEnabled(false);
                        socket.close();
                        break;
                    }
                    // System.out.println("CLIENT :"+msg);
                    textarea.append("SERVER : "+msg+"\n");
                }

            } catch (Exception e) {
                System.out.println(e);
            }
           

          };
          new Thread(r1).start();
    }
    private void creategui()
{
    this.setTitle("CLIENT MESSENGER");
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
    textarea.setBackground(Color.RED);
    
    messageInput.setFont(font);

    // send.setFont(font);
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
                
                // System.out.println("key released code"+e.getKeyCode());  /// this can be used to get the key code of enter i.e. 10
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
        );
    }
    
    
//     public void startWriting()
//     {
             
//         Runnable r2=()->{
//             System.out.println("writer started ...");
//             try {
//                 while(!socket.isClosed())
//                 {
//                 BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
//                 String content=br1.readLine();
                
//                 out.println(content);
//                 out.flush();
//                 if(content.equals("exit"))
//                 {
//                     socket.close();
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
        System.out.println("this is my client class");
        new client();
        
    }
}
