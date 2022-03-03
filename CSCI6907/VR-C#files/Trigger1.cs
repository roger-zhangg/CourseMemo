using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Trigger1 : MonoBehaviour
{
    private void OnTriggerEnter(Collider other)
    {
        if (Score.stage == 0){
            Debug.Log("Triggered by "+other.gameObject.tag);
            if(other.gameObject.CompareTag("Player")){
                Score.stage++;
            }

        }
    }
}
