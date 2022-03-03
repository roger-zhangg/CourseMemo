using System.Collections;

using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class Score : MonoBehaviour
{
    public GameObject scoreText;
    public Transform body;
    public static int distance;
    public static int jumps;
    
    public static int stage;
    public static string questText;
    public static int flower;
    public static int mushroom;



    // Update is called once per frame
    void Update()
    {
        if (body.hasChanged){
            jumps++;
            body.hasChanged = false;
        }
        if (stage == 0){
            questText = "Find the talking book (Press B to show menu)";
        }else if (stage == 1){
            questText = "Follow the guide line";
        }else if (stage == 2){
            questText = "Collect the required material";
        }else if (stage == 3){
            questText = "Collect the other required material";
        }else if (stage == 4){
            questText = "Follow the line back to alteria";
        }else if (stage == 5){
            questText = "You Win (Press B to show Score)";
        }

        
        scoreText.GetComponent<Text>().text = "Quest: "+questText+"\n";
    }
}
