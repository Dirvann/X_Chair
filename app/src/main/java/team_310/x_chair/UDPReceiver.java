package team_310.x_chair;

/**
 * Created by Dirk Vanbeveren on 6/04/2017.
 */

/*class UDPReceiver {

        /*public void run(int port) {
            /*try {
                DatagramSocket serverSocket = new DatagramSocket(port);
                byte[] receiveData = new byte[8];

                System.out.printf("Listening on udp:%s:%d%n",
                        InetAddress.getLocalHost().getHostAddress(), port);
                DatagramPacket receivePacket = new DatagramPacket(receiveData,
                        receiveData.length);

                while (true) {
                    serverSocket.receive(receivePacket);
                    String sentence = new String(receivePacket.getData(), 0,
                            receivePacket.getLength());
                    System.out.println("RECEIVED: " + sentence);
                }
            }catch (IOException e) {
                System.out.println(e);
            }
}*/

