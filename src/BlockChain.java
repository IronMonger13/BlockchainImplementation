package src;

import java.util.ArrayList;

public class BlockChain {

	public static ArrayList<Block> blockchain = new ArrayList<Block>(); // list to store the blockchain
	public static int difficulty = 5; // difficulty level for proof-of-work algorithm

	public static void main(String[] args) {
		// add our blocks to the blockchain ArrayList

		System.out.println("Trying to Mine block 1");
		addBlock(new Block("This is the first block", "0"));

		System.out.println("Trying to Mine block 2");
		addBlock(new Block("This is the second block", blockchain.get(blockchain.size() - 1).hash));

		System.out.println("Trying to Mine block 3");
		addBlock(new Block("This is the third block", blockchain.get(blockchain.size() - 1).hash));

		System.out.println("\nBlockchain is Valid: " + isChainValid());

		// converting blockchain to JSON format for readability
		String blockchainJson = StringUtil.getJson(blockchain);
		System.out.println("\nThe block chain: ");
		System.out.println(blockchainJson);
	}

	// Method to verify integrity of blockchain
	public static Boolean isChainValid() {
		Block currentBlock;
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0'); // target hash based on difficulty (a
																					// string of leading 0s)

		// loop through blockchain to check hashes
		for (int i = 1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i - 1);

			// compare registered hash and calculated hash
			if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
				System.out.println("Current Hashes are not equal");
				return false;
			}

			// compare previous hash and registered previous hash
			if (!previousBlock.hash.equals(currentBlock.previousHash)) {
				System.out.println("Previous Hashes are not equal");
				return false;
			}

			// Check if the block's hash satisfies the difficulty level
			if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined");
				return false;
			}

		}
		return true;
	}

	// Method to mine a new block and add it to blockchain
	public static void addBlock(Block newBlock) {
		newBlock.mineBlock(difficulty);
		blockchain.add(newBlock);
	}
}