using UnityEngine;
using System.Collections;
using TMPro;
using System;

public class TextMesh2  : MonoBehaviour
{
    public static DateTime startTime;
    public TextMesh  textDisplay;

    void Start(){
            startTime = DateTime.Now;
    }

    void LateUpdate()
    {  
        TimeSpan interval = DateTime.Now - startTime;
        string msg = String.Format("Elapsed Time: {0:hh\\:mm\\:ss}",interval);
        textDisplay.text = "Distance Traveled: " + Score.distance + "\n" + "Jumps: " + Score.jumps +"\n" + msg +"\n" +"Mushroom collected: " + Score.mushroom +"\nFlower collected: "+Score.flower;
    }
}
