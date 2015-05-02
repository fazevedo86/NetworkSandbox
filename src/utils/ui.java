package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ui {

	public static String readInput()
	{
		BufferedReader inputBuffer = new BufferedReader(new InputStreamReader(System.in));
		String inputText = null;
		
		try {
			inputText = inputBuffer.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return inputText;
	}

}
