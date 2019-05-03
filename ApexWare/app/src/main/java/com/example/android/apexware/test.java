package com.example.android.apexware;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.android.apexware.atv.model.TreeNode;
import com.example.android.apexware.atv.view.AndroidTreeView;
import com.onesignal.OneSignal;

public class test extends AppCompatActivity {
    TreeNode root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        try{


        //Root
         root = TreeNode.root();

        //Parent
        MyHolder.IconTreeItem nodeItem = new MyHolder.IconTreeItem(R.drawable.ic_arrow_drop_down, "Parent");
        TreeNode parent = new TreeNode(nodeItem).setViewHolder(new MyHolder(getApplicationContext(), true, MyHolder.DEFAULT, MyHolder.DEFAULT));

        //Child
        MyHolder.IconTreeItem childItem = new MyHolder.IconTreeItem(R.drawable.ic_folder, "Child");
        TreeNode child = new TreeNode(childItem).setViewHolder(new MyHolder(getApplicationContext(), false, R.layout.child, 25));

        //Sub Child
        MyHolder.IconTreeItem subChildItem = new MyHolder.IconTreeItem(R.drawable.ic_folder, "Sub Child");
        TreeNode subChild = new TreeNode(subChildItem).setViewHolder(new MyHolder(getApplicationContext(), false, R.layout.child, 50));
        //Sub Child
         MyHolder.IconTreeItem subChildItem2 = new MyHolder.IconTreeItem(R.drawable.ic_folder, "Sub Child");
         TreeNode subChild2 = new TreeNode(subChildItem2).setViewHolder(new MyHolder(getApplicationContext(), false, R.layout.child, 75));


        //Add sub child.
        child.addChild(subChild);
        subChild.addChild(subChild2);

        //Add child.
        parent.addChildren(child);
        root.addChild(parent);

            //Add AndroidTreeView into view.
            AndroidTreeView tView = new AndroidTreeView(getApplicationContext(), root);
            ((LinearLayout) findViewById(R.id.ll_parent)).addView(tView.getView());


            Button button=(Button)findViewById(R.id.testButton);
            button.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Parent
                            MyHolder.IconTreeItem nodeItem44 =
                                    new MyHolder.IconTreeItem(R.drawable.ic_arrow_drop_down, "Parent");
                            TreeNode parent2 =
                                    new TreeNode(nodeItem44)
                                            .setViewHolder(
                                                    new MyHolder(
                                                            getApplicationContext(), true, MyHolder.DEFAULT, MyHolder.DEFAULT));
                            root.addChild(parent2);
                            TreeNode.BaseNodeViewHolder.tView.expandNode(root);
                        }
                    });
}
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
