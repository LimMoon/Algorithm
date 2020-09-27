import java.util.*;

class TreeNode {
  int val;
  TreeNode left;
  TreeNode right;
  TreeNode() {}
  TreeNode(int val) { this.val = val; }
  TreeNode(int val, TreeNode left, TreeNode right) {
    this.val = val;
    this.left = left;
    this.right = right;
  }
}

class NodeInfo implements Comparable<NodeInfo> {
  int x;
  int y;
  int id;
  NodeInfo(int x, int y, int id){
    this.x = x;
    this.y = y;
    this.id = id;
  }
  @Override
  public int compareTo(NodeInfo nodeInfo) {
    return Integer.compare(y, nodeInfo.y);
  }
}

class Solution {
  List<Integer> preorder;
  List<Integer> postorder;
  public int[][] solution(int[][] nodeinfo) {
    PriorityQueue<NodeInfo> pQueue =  
      new PriorityQueue<NodeInfo>(Collections.reverseOrder());

    for(int i = 0; i < nodeinfo.length; i++){
      NodeInfo nodeInfo = new NodeInfo(nodeinfo[i][0], nodeinfo[i][1], i+1);
      pQueue.add(nodeInfo);
    }

    // create binary tree
    // find max y, set that as root.
    TreeNode root = createBinaryTree(pQueue);

    //preorder
    preorder = new ArrayList<Integer>();
    preorderVisit(root);

    //postorder
    postorder = new ArrayList<Integer>();
    postorderVisit(root);

    int[] prearray = preorder.stream().mapToInt(i->i).toArray();
    int[] postarray = postorder.stream().mapToInt(i->i).toArray();

    int[][] answer = {prearray, postarray};
    return answer;
  }
    
  public TreeNode createBinaryTree(PriorityQueue<NodeInfo> pQueue){
    if(pQueue.size() == 0) return null;

    //new root
    NodeInfo nodeInfo = pQueue.poll();
    TreeNode root = new TreeNode(nodeInfo.id);
    int rootX = nodeInfo.x;

    if(pQueue.size() == 0){
      return root;
    }

    PriorityQueue<NodeInfo> left = new PriorityQueue<NodeInfo>(Collections.reverseOrder());
    PriorityQueue<NodeInfo> right = new PriorityQueue<NodeInfo>(Collections.reverseOrder());
    Iterator<NodeInfo> itr = pQueue.iterator(); 
    while (itr.hasNext()) {
      NodeInfo curr = itr.next();
      if(curr.x > rootX){
        right.add(curr);
      }else{
        left.add(curr);
      }
    }

    if(!left.isEmpty()){
      root.left = createBinaryTree(left);
    }

    if(!right.isEmpty()){
      root.right = createBinaryTree(right);
    }
    return root;
  }

  public void preorderVisit(TreeNode root){
    if(root == null){
      return;
    }
    preorder.add(root.val);
    preorderVisit(root.left);
    preorderVisit(root.right);
  }

  public void postorderVisit(TreeNode root){
    if(root == null){
      return;
    }
    postorderVisit(root.left);
    postorderVisit(root.right);
    postorder.add(root.val);
  }
}
