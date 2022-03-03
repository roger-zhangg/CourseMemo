using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Teleported : MonoBehaviour
{
    // Start is called before the first frame update
    void Start()
    {
        Score.jumps++;
    }

    // Update is called once per frame
    void Update()
    {
        Score.jumps++;
    }

    void TeleportUpdate(){
        Score.jumps++;
    }
}
