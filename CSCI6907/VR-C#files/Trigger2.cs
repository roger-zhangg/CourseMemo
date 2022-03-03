using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Trigger2 : MonoBehaviour
{
    private void OnTriggerEnter(Collider other)
    {
        if (Score.stage == 1){
            Debug.Log("Triggered by "+other.gameObject.tag);
            if(other.gameObject.CompareTag("Player")){
                Score.stage++;
            }

        }
        if (Score.stage == 4){
            Debug.Log("Triggered by "+other.gameObject.tag);
            if(other.gameObject.CompareTag("Player")){
                Score.stage++;
            }

        }
    }
}
