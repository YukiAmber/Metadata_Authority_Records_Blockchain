package Metadata_Pkg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.GsonBuilder;

public class MetadataChain {
	

	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static int difficulty = 5;

	public static void main(String[] args) throws IOException 
	{	
		//FileInput metadatarecord = new FileInput(); //propbably need to pass something here
		//UserInformation editor = new UserInformation(); 
		
		 String editor = UserInformation();
		 String metadatarecord = FileInput();
		 
		 System.out.println("Your name is: " + editor);
		 System.out.println("Metadatarecord is: " + metadatarecord);
		
		System.out.println("Trying to Mine block 1... ");
		addBlock(new Block(metadatarecord, "0"));
		
		System.out.println("Trying to Mine block 2... ");
		addBlock(new Block(metadatarecord, blockchain.get(blockchain.size()-1).hash));
		//System.out.println("Trying to Mine block 3... ");
		//addBlock(new Block(metadatarecord, blockchain.get(blockchain.size()-1).hash));
		

		
		System.out.println("\nBlockchain is Valid: " + isChainValid());
		
		String blockchainJson = StringUtil.getJson(blockchain);
		System.out.println("\nThe block chain: ");
		System.out.println(blockchainJson);
		// System.out.println(editor);
		
		
	}
	
	public static Boolean isChainValid() 
	{
		Block currentBlock; 
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		
		//loop through blockchain to check hashes:
		for(int i=1; i < blockchain.size(); i++) 
		{
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);
			
			//compare registered hash and calculated hash:
			if(!currentBlock.hash.equals(currentBlock.calculateHash()) )
			{
				System.out.println("Current Hashes not equal");			
				return false;
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.hash.equals(currentBlock.previousHash) ) 
			{
				System.out.println("Previous Hashes not equal");
				return false;
			}
			
			//check if hash is solved
			if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) 
			{
				System.out.println("This block hasn't been mined");
				return false;
			}			
		}
		return true;
	}
	
	public static void addBlock(Block newBlock) 
	{
		newBlock.mineBlock(difficulty);
		blockchain.add(newBlock);
	}
	
	//get the User name   
	public static String UserInformation() throws IOException 
	{	
		Scanner usernamereader = new Scanner(System.in);
	    
			System.out.println("Username?");
			String username = usernamereader.nextLine();
			//System.out.println(username);visual output to ensure reader is working properly
			return username;
	}
	
//Get file input information	
	public static String FileInput() throws IOException 
	{		
		   StringBuilder metadatarecordfile = new StringBuilder();
		   String Line = null;
		// single line reader
	        List<String> Lines = new ArrayList<String>();
			
		   Scanner FileScanner = new Scanner(System.in);
			
//find out where the file is and get it	        
	        System.out.println("What is the full file path and name?");
	        String Input = FileScanner.nextLine();
	        File FileName = new File(Input);
	        
	        	        
// read input
	        try 
	        {
	        	FileReader FileReader =  new FileReader(FileName);	          
	            BufferedReader BufferedReader =  new BufferedReader(FileReader);

	            while((Line = BufferedReader.readLine()) != null) 
	            {
	            	//Lines.add(Line);
	            	metadatarecordfile.append(Line);
	            	//stringBuffer.append(line);
	            	//System.out.println(Lines); visual output to ensure reader is working properly
	            }	
		        // close file
		        BufferedReader.close(); 
	        }
	        	        
 //Minor Error Handling
	        catch(FileNotFoundException ex)
	        {  System.out.println( "Unable to open file '" +  FileName + "'");}
	        
	        catch(IOException ex) 
	        {  System.out.println( "Error reading file '" + FileName + "'"); }
				               	            

	        /*Return the file in an array*/
			//return Lines.toArray(new String[Lines.size()]);
	        return metadatarecordfile.toString();
	    }
	

}
