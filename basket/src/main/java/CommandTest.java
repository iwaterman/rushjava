import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * CommandTest
 * 
 * @author opencfg.com
 * @since 0.0.1-SNAPSHOT
 * @version 0.0.1-SNAPSHOT
 * @date 2011-05-17
 */
public class CommandTest {

    public static void main(String[] args) throws Exception {
	// 1.test console args commands
	// exec("args", args);

	String[] commands = new String[] { "/bin/bash", "-c",
		"grep -h 200.*370.*http /var/log/nginx/access.log.* > /root/test_123.log" };
	String[] commands_ls = new String[] { "/bin/bash", "-c", "ls /var/log/nginx/access.log.*" };

	// 1.test java string commands
	exec("commands", commands);
    }

    public static void exec(String message, String[] args) throws Exception {
	print(message + ":");
	Process process = Runtime.getRuntime().exec(args);
	for (String arg : args) {
	    System.out.println(arg);
	    System.out.print(" ");
	}
	BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	String line = null;
	while ((line = errorReader.readLine()) != null) {
	    System.err.println(line);
	}
	errorReader.close();
	BufferedReader infoReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
	while ((line = infoReader.readLine()) != null) {
	    System.out.println(line);
	}
	infoReader.close();
	print("");
    }

    public static void print(String[] args) {
	for (String arg : args) {
	    System.out.println(arg);
	    System.out.print(" ");
	}
    }

    public static void print(String arg) {
	System.out.println(arg);
    }

}