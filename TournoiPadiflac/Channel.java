package TournoiPadiflac;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Objects;
import java.util.Queue;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;


public class Channel {
    private final String channel_name;
	private Queue<byte[]> events = new ArrayDeque<>();
	private int id = 0;
	private static final String addr = "https://prog-reseau-m1.lacl.fr/padiflac/";
	private static final String nonce =
			  "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final Random r = new Random();
	
	private String genNonce() {
		char[] sb = new char[64];
		for(int i=0; i<64;i++) {
			sb[i] = nonce.charAt(r.nextInt(62));
		}
		return new String(sb);
	}
	
	public Channel(String channel_name) {
		this.channel_name = channel_name;
	}
	
	private byte[] serializeToString(String o) throws IOException {
		return o.getBytes() ;
	}	
	
	private void parseEvent(byte[] bb, int i) {
		StringBuilder sb = new StringBuilder();
		while(i<bb.length) {
			byte b = bb[i];
			if(b=='|')break;
			sb.append((char)b);
			i++;
		}
		i++;
		if(i<bb.length) {
			int size = Integer.parseInt(sb.toString());
			byte[] buff = java.util.Arrays.copyOfRange(bb, i, i+size);
			events.add(buff );
			parseEvent(bb, i+size);
		}else {
			id =Integer.parseInt(sb.toString());
		}
		
	
	}
	
	public void connect() {
		String id2 = "";
		if (id>0) {
			id2 = "?id="+id;
		}
		URL u;
		try {
			u = new URL(addr+channel_name+id2);
			URLConnection uc = u.openConnection();
			InputStream is = uc.getInputStream();
			byte[] bs=is.readAllBytes();
			parseEvent(bs,0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getNext() {
		while (events.isEmpty()) {
			connect();
		}
		return new String(events.poll());
	}
	
	
	
	public void send(String s)  {
		try {
			String non = genNonce();
			URL u = new URL(addr+channel_name+"?nonce="+non);
			HttpsURLConnection uc = (HttpsURLConnection)u.openConnection();
			uc.setRequestMethod("POST");
			uc.setDoOutput(true);
			OutputStream os = uc.getOutputStream();
			os.write(serializeToString(s));
			os.close();
			uc.connect();
			uc.getInputStream();		
			//id = Integer.parseInt(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	
	public static void test() {
		Channel c = new Channel("PippoTest");
		c.connect();
		long time=new Date().getTime();
		//System.out.println(time);
		c.send(Objects.toString(time));
		c.send("TestJavaObjectToto" + time);
		boolean flag=false;
		long l1=0;
		for(;;) {
			String s= c.getNext();
			try {l1 = Long.parseLong(s);} catch (NumberFormatException e) {}			
			if (l1==time) flag=true;
			if(flag) System.out.println(s);
		}
	}

	
	
	public static void main(String[] args)  {
		
	}
	
}