/**
 * An implementation of the Map interface using a red-black tree.
 * Empty trees are used to represent children that aren't present.
 * 
 * @author John Donaldson (Spring, 2018)
 */

// STUDENTS need to implement the following methods to complete the class:
//  - get()
//  - put()
//  - optionally add in a remove method

import java.util.*;

public class RBTreeMap<K extends Comparable<? super K>,V> 
implements MapADT<K,V> {
    static public int RED=1, BLACK=2; // two possible colors

    int color;  /** The color of this node (RED or BLACK)  */
    K key;      /** The index by which information is stored */
    V value;    /** Value associated with the key above */
    RBTreeMap<K,V> left,right; /** subtrees of this subtree -- if both null, this tree is empty */

    // change this value to true and the rotations will be printed
    private boolean debug = false;

    //-----------------------------------------------------------------------
    // Things TODO - I've moved all the methods you need to change up to here
    //-----------------------------------------------------------------------

    // TODO - implement me!
    public V get(K searchKey) {
    	if (key.compareTo(searchKey) == 0) {
    		return value;
    	}
    	if (isEmpty()) {
    		return null;
    	}
    	V retval = left.get(searchKey);
    	if (retval != null) {
    		return retval;
    	}
    	return right.get(searchKey);
    }

    // public version of the put method -- Don't change it!!
    public V put(K key, V value){
	if(debug){
	    System.out.println("putting ("+key+","+value+")");
	}
	// this is the root of the entire tree, so let parent and gran to null
	return put(key,value,null,null);
    }
    
    // TODO - implement me too!
    protected V put(K key, V value, RBTreeMap<K,V> parent, RBTreeMap<K,V> gran){
       V rvalue = null;  // variable to hold the return value

       // insert node or update value
       if(isEmpty()){   // base case I:  empty tree
    	   this.key = key;
    	   this.value = value;
    	   if (parent == null) {
    		   color = BLACK;
    	   } else {
    		   color = RED;
    	   }
    	   left = new RBTreeMap<K,V>();
    	   right = new RBTreeMap<K, V>();
       }
       else {
	   // TODO:  implement the other put cases
          int comp = key.compareTo(this.key);
          if(comp==0){ // base case II:  key is already present
        	  rvalue = this.value;
        	  this.value = value;
          }
          else if(comp<0){  // recursive case I:  put in left subtree
        	  rvalue = left.put(key, value, this, parent);
          }
          else {  // recursive case II: put in right subtree
        	  rvalue = right.put(key, value, this, parent);
          }
       }

       //  After the key-value pair has been inserted, check to see if any
       //  adjustments are necessary
       if(parent==null){
    	   color = BLACK;
       }
       else if(color==RED && parent.color==RED){
	   // consecutive red nodes -- do something!

	   // first, find the uncle
	   RBTreeMap<K,V> uncle = null;
	   if(parent.key.compareTo(gran.key)<0)
	       uncle = gran.right;
	   else
	       uncle = gran.left;
	   
	   if(uncle.color==RED){
	       uncle.color = BLACK;
	   }
	   else {
	       // adjustment case II:  the uncle is black, so rotate
	       // TODO:  Implement this case
	       /*
		 You need to determine which rotation case (LL, LR, RL, RR)
		 to apply.  For example, if this.key < parent.key < gran.key,
		 this is the LL case; if this.key < parent.key and
		 gran.key < parent.key, this is the RL case
	       */
		 int pcomp = this.key.compareTo(parent.key);
		 int gcomp = parent.key.compareTo(gran.key);
	     /*
		 Then apply the appropriate rotation:
		 LL: single right rotation*/
		 if (pcomp < 0 && gcomp < 0) {
			 rotateRight(parent);
		 }
		 /*LR: double right rotation (a left rotation at p followed 
                     by a right rotation at g)*/
		 if (pcomp > 0 && gcomp < 0) {
			 rotateLeft(parent);
			 rotateRight(gran);
		 }
		 //RR: single left rotation
		 if (pcomp > 0 && gcomp > 0) {
			 rotateLeft(parent);
		 }
		 /*RL: double left rotation (a right rotation at p followed
                     by a left rotation at g)
	       */
		 if (pcomp < 0 && gcomp > 0) {
			 rotateRight(parent);
			 rotateLeft(gran);
		 }
	   }
       }
       // return this key's previous value
       return rvalue;
    }

    /*
      I've written the rotation methods for you
    */    
    private void rotateLeft(RBTreeMap<K,V> g){
	if(debug){
	    System.out.println("rotate left");
	}
	RBTreeMap<K,V> t0,t1,p;
       
       p = g.right;
       t0 = g.left;
       t1 = p.left;
       
       // swap contents of g and p
       K key = g.key;
       V value = g.value;
       
       g.key = p.key;
       g.value = p.value;
       
       p.key = key;
       p.value = value;
       
       // adjust pointers
       g.left = p;
       g.right = p.right;
       p.left = t0;
       p.right = t1;
    }

    private void rotateRight(RBTreeMap<K,V> g){
	if(debug){
	    System.out.println("rotate right");
	}
	RBTreeMap<K,V> t2,t3,p,temp;

	p = g.left;
	t2 = p.right;
	t3 = g.right;
       
	// swap g and p
	K key = g.key;
	V value = g.value;
       
	g.key = p.key;
	g.value = p.value;
       
	p.key = key;
	p.value = value;
       
	// adjust pointers
	g.left = p.left;
	g.right = p;
	p.left = t2;
	p.right = t3;
    }
    
    //----------------------------------------------------------------------
    // ** NO CHANGES NEEDED BELOW - BUT YOU MIGHT WANT TO READ AND UNDERSTAND
    //----------------------------------------------------------------------


    //-----------------------------------------------------------------------
    // Constructors - outside users can only make an empty tree
    //-----------------------------------------------------------------------

    /*
     * Creates an empty tree
     */ 
    public RBTreeMap(){
        this(null,null,null,null);
        this.color = BLACK;
    }
    
    
    /**
     * Build a tree from existing components.  To be used only within RBTreeMap
     * and subchildren.  
     * @param key Key to store at this location
     * @param value Value to store at this location (associated with Key)
     * @param left An existing subtree, all Key values should be smaller than key
     * @param right An existing subtree, all Key values should be greater than key
     */
    
    protected RBTreeMap(K key, V value, RBTreeMap<K, V> left, RBTreeMap<K, V> right) {
        this.key = key;
        this.value = value;
        this.left = left;
        this.right = right;
    }
    
    
    /**
     * Used to construct a single leaf element.  Empty subtrees are put in place of
     * children to allow for recursive methods to not have to worry about null
     * pointers.
     * @param key Key to store at this location
     * @param value Value to store at this location (associated with Key)
     */
    
    protected RBTreeMap(K key, V value) {
        this(key,value,new RBTreeMap<K,V>(), new RBTreeMap<K,V>());
    }
    
    /**
     * Determine if this is a placeholder subtree for an empty leaf branch.
     * @return true only if this is a placeholder node, false otherwise
     */
    public boolean isEmpty(){
        return left==null && right==null;
    } 

    /*
     * helper method for the public verify method
     */
    private boolean verify(RBTreeMap<K,V> parent){
	if(isEmpty())
	    return true;
	else if(parent==null && color!=BLACK){
	    System.out.println("verify: root is not black "+key);
	    return false;
	}
	else if(color==RED && (left.color==RED || right.color==RED)){
	    System.out.println("verify colors failed at "+key);
	    return false;
	}
	else if(left.getBlackPathLength()!=right.getBlackPathLength()){
	    System.out.println("verify black path length failed at "+key);
	    return false;
	}
	else if(!left.isEmpty() && left.key.compareTo(key)>0){
	    System.out.println("verify order failed at "+key+" (left="+left.key+")");
	    return false;
	}
	else if(!right.isEmpty() && right.key.compareTo(key)<0){
	    System.out.println("verify order failed at "+key+" (right="+right.key+")");
	    return false;
	}
	else {
	    return left.verify(this) && right.verify(this);
	}
    }

    /**
     * Determine if this is a valid red-black tree
     * @return true only if this tree passes all tests; false otherwise
     */
    public boolean verify(){
	return verify(null);
   }

    /**
     * Creates a StringBuffer (String takes too long) and then starts the
     * recursive traversal of nodes.
     * @return String representation of the (Key:Value) pairs
     */
    private String toStringHelper(){
        StringBuilder sbuf = new StringBuilder();
        toStringHelper(sbuf,0);
        return sbuf.toString();
    }

    /**
     * Adds (Key:Value) pairs inorder into the StringBuffer.
     * @param sbuf where to store the string version of the tree
     */
    private void toStringHelper(StringBuilder sbuf, int level) {
        if(isEmpty())
            return;
        else {
            if(!left.isEmpty()) {
                left.toStringHelper(sbuf,level+1);
                sbuf.append(",");
            }
            sbuf.append( "("+key+":"+value+":"+((color==RED)?"R":"B")+":"+level+")" );
            if(!right.isEmpty()) {
                sbuf.append(",");
                right.toStringHelper(sbuf,level+1);
            }
        }
    }

    public String toString(){
        return "[" + toStringHelper() + "]";
    }

    /**
     * Determines the height of this tree
     * @return the height of this tree
     */
    public int getHeight() {
	if(isEmpty())
	    return -1;
	else
	    return 1+Math.max(left.getHeight(),right.getHeight());
    }
    
    /**
     * Determines the number of (key,value) pairs in this map
     * @return the number of (key,value) pairs
     */
    public int size() {
       if(isEmpty())
          return 0;
       else
	  return 1+left.size()+right.size();
    }

    /**
     * Determines the number of black nodes on any path from root
     * to leaf in this red-black tree
     * @return the 
     */
    public int getBlackPathLength(){
	if(isEmpty())
	    return 0;
	else {
	    int lbpl = left.getBlackPathLength();
	    int rbpl = right.getBlackPathLength();
	    return lbpl + ((color==BLACK)?1:0);
	}
    }

    /**
     * Implementation of the clear method of java's Map interface
     * Removes all key-value pairs from this Map
     */
    public void clear(){
	color=BLACK;
	key = null;
	value = null;
	left = right = null;
    }

    //-----------------------------------------------------------------------
    // Some iterators that you might find useful
    //-----------------------------------------------------------------------

    /**
     * Create an inorder iterator of the Keys in the tree.
     * @return inorder iterator of the Keys
     */
    
    public Iterator<K> keys() {
        List<K> list = new LinkedList<K>();
        addKeysToList(list);
        return list.iterator();
    }
    
    private void addKeysToList(List<K> l) {
       if (!isEmpty()){
          this.left.addKeysToList(l);
          l.add(this.key);
          this.right.addKeysToList(l);
       }
    }

    //-----------------------------------------------------------------------

    /**
     * Create an inorder iterator of the key-value pairs in the tree.
     * @return inorder iterator of the key-value pairs
     */
    
    public Iterator<Map.Entry<K, V>> entries() {
        List<Map.Entry<K,V>> list = new LinkedList<Map.Entry<K,V>>();
        addEntriesToList(list);
        return list.iterator();
    }
    
    private void addEntriesToList(List<Map.Entry<K, V>> list) {
       if (!isEmpty()){
          this.left.addEntriesToList(list);
          list.add(new AbstractMap.SimpleEntry<K,V>(key,value));
          this.right.addEntriesToList(list);
       }
    }    
}
