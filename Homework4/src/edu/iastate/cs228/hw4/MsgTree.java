package edu.iastate.cs228.hw4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Stack;

/**
 * @author Westin Gjervold
 * 
 *         This class reconstructs/unzips a message archived with a
 *         binary-tree-based algorithm. The program should ask for a single
 *         filename, decode the message in the file and print it out to the console.
 */
public class MsgTree {
	public char payloadChar;
	public MsgTree left;
	public MsgTree right;

	/*
	 * Constructor building the tree from a string
	 * 
	 * @param encodingString
	 */
	public MsgTree(String encodingString) {
		//Checks if encodingString is null or too short
		if (encodingString == null || encodingString.length() < 2) {
			return;
		}
		Stack<MsgTree> stack = new Stack<>();
		int index = 0;
		char lastPayloadChar = '^';
		payloadChar = encodingString.charAt(index);
		stack.push(this);
		MsgTree currentTree = this;
		while (index < encodingString.length()-1) {
			index++;
			char currentChar = encodingString.charAt(index);
			MsgTree tempNode = new MsgTree(currentChar);
			if (lastPayloadChar == '^') {
				currentTree.left = tempNode;

			} else {
				currentTree.right = tempNode;

			}
			if (currentChar == '^') {
				currentTree = stack.push(tempNode);
				lastPayloadChar = tempNode.payloadChar;
			}
			else {
				if (!stack.empty()) {
					currentTree = stack.pop();
				}
				lastPayloadChar = tempNode.payloadChar;
			}
		}
	}

	/*
	 * Constructor for a single node with null children
	 * 
	 * @param payloadChar
	 */
	public MsgTree(char payloadChar) {
		this.payloadChar = payloadChar;
		this.left = null;
		this.right = null;
	}

	/*
	 * Method to print characters and their binary codes
	 * 
	 * @param root
	 * 
	 * @param code
	 */
	public static void printCodes(MsgTree root, String code) {
		//Checks if root is a leaf
		if (root.left != null && root.right != null) {
			printCodes(root.left, code + "0");
			printCodes(root.right, code + "1");
		} 
		//If root is leaf print character and code
		else {
			char curPayloadChar = root.payloadChar;
			if(curPayloadChar == ' ') {
				System.out.println("SPACE\t\t"+ code);
			}
			else if(curPayloadChar == '\t') {
				System.out.println("TAB\t\t"+ code);
			}
			else if(curPayloadChar == '\n') {
				System.out.println("NEWLINE\t\t"+ code);
			}
			else {
				System.out.println(curPayloadChar + "\t\t" + code);
			}
		}
	}

	/*
	 * Method to print the decoded message to the console
	 * 
	 * @param codes
	 * 
	 * @param msg
	 */
	public void decode(MsgTree codes, String msg) {
		System.out.println("MESSAGE:");
		MsgTree cur = codes;
		StringBuilder decodedMsgBuilder = new StringBuilder();
		for (int i = 0; i < msg.length(); i++) {
			//Searches through tree
			char currentChar = msg.charAt(i);
			if (currentChar == '0') {
				cur = cur.left;
			} else {
				cur = cur.right;
			}
			//If payloadChar != ^ add payloadChar to decodedMsg
			if (cur.payloadChar != '^') {
				decodedMsgBuilder.append(cur.payloadChar);
				cur = codes;
			}
		}
		//Prints decodedMsg
		String decodedMsg = decodedMsgBuilder.toString();
		System.out.println(decodedMsg);
		statistics(msg, decodedMsg);
	}

	/*
	 * Prints the statistics of decoding the message
	 * 
	 * @param encodeStr
	 * 
	 * @param decodeStr
	 */
	private void statistics(String encodeStr, String decodeStr) {
		System.out.println();
		System.out.println("STATISTICS:");
		System.out.printf("Avg bits/char:        %.1f\n", encodeStr.length() / (double) decodeStr.length());
		System.out.printf("Total Characters:     %d\n", decodeStr.length());
		System.out.printf("Space Saving:         %.1f%%",
				(1d - decodeStr.length() / (double) encodeStr.length()) * 100);
	}

	/*
	 * Main Method
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		//Gets fileName from user
		System.out.println("Please enter filename to decode:");
		Scanner fileNameScanner = new Scanner(System.in);
		String fileName = fileNameScanner.nextLine();
		fileNameScanner.close();
		//Reads through file and creates pattern string and encodedMsg String
		String fileString = new String(Files.readAllBytes(Paths.get(fileName))).trim();
		int patternEnd = fileString.lastIndexOf('\n');
		String pattern = fileString.substring(0, patternEnd);
		String encodedMsg = fileString.substring(patternEnd).trim();
		//Output
		MsgTree root = new MsgTree(pattern);
		System.out.println("character\tcode");
		System.out.println("------------------------");
		MsgTree.printCodes(root, "");
		root.decode(root, encodedMsg);
	}
}
