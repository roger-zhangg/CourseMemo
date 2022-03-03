using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DistanceCal : MonoBehaviour
{   
    
    Vector3 playerLastPosition;
    // Start is called before the first frame update
    void Start()
    {
        playerLastPosition = transform.position;
    }

    // Update is called once per frame
    void LateUpdate()
    {
        float dist = Vector3.Distance(playerLastPosition, transform.position);
        Score.distance += (int)dist;
        playerLastPosition = transform.position;
    }
}
