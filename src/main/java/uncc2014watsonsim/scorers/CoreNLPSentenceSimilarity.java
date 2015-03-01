package uncc2014watsonsim.scorers;

import java.util.HashSet;
import java.util.List;

import edu.stanford.nlp.trees.Tree;
import uncc2014watsonsim.Answer;
import uncc2014watsonsim.Passage;
import uncc2014watsonsim.Question;
import uncc2014watsonsim.nlp.Trees;

/* @author Wlodek
 * @author Sean Gallagher
 * 
 * Create a score based on how many parse trees the question, candidate answer
 * and passage have in common.
 * 
 * This scorer can be very slow.
 */

public class CoreNLPSentenceSimilarity extends PassageScorer {
	/**
	 * Score the similarity of two sentences according to
	 * sum([ len(x) | x of X, y of Y, if x == y ])
	 * where X and Y are the sets of subtrees of the parses of s1 and s2.  
	 * @param x
	 * @param y
	 * @return
	 */
	public double scorePhrases(List<Tree> t1, String s2) {
		List<Tree> t2 = Trees.parse(s2);
		
		HashSet<Tree> t1_subtrees = new HashSet<>();
		HashSet<Tree> t2_subtrees = new HashSet<>();
		for (Tree x : t1) t1_subtrees.addAll(x);
		for (Tree y : t2) t2_subtrees.addAll(y);
		t1_subtrees.retainAll(t2_subtrees);
		
		double score = 0.0;
		// x.getLeaves().size() may also be a good idea.
		// I don't have any intuition for which may be better.
		for (Tree x : t1_subtrees) score += x.size();
		return score;
	}
		

	/** Generate a simple score based on scorePhrases.
	 * 
	 */
	public double scorePassage(Question q, Answer a, Passage p) {
		return scorePhrases(p.parsed, a.candidate_text);
	}
}

