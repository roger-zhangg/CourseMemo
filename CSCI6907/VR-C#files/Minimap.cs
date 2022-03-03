using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Minimap : MonoBehaviour
{
    [SerializeField]
    private Transform Player;


    void LateUpdate()
    {
        Vector3 playerPosition = Player.position;

        playerPosition.y = transform.position.y;
        transform.position = playerPosition;
        float yRotation = Player.eulerAngles.y;
        transform.eulerAngles = new Vector3( transform.eulerAngles.x, yRotation, transform.eulerAngles.z );
    }
}