package Metadata_Pkg;

import java.util.Date;

public class Block 
{
	
	public String hash;
	public String previousHash; 
	public String metadata; 
	private long timeStamp; 
	private int counter;
	
	//Block Constructor.  
	public Block(String metadatarecord, String previousHash)
	{
		this.metadata = metadatarecord;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		
		this.hash = calculateHash();
	}
	
	//Calculate new hash and output
	public String calculateHash() 
	{
		String calculatedhash = StringUtil.applySha256(previousHash + Long.toString(timeStamp) + Integer.toString(counter) + metadata);
		return calculatedhash;
	}
	
	//Increases counter value until hash target is reached.
	public void mineBlock(int difficulty) 
	{		
		String target = StringUtil.getDificultyString(difficulty); 
		
		while(!hash.substring(0, difficulty).equals(target)) 
		{
			counter++;
			hash = calculateHash();
		}
		
		System.out.println("Block Mined: " + hash);
	}
	
}
