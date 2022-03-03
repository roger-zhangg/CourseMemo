using UnityEngine;
using UnityEngine.AI;


public class DrawNavLine : MonoBehaviour
{
    [SerializeField]
    private Transform target;
    [SerializeField]
    private LineRenderer Path;
    [SerializeField]
    private Transform Player;
    [SerializeField]
    private float PathHeightOffset = 0.5f;

    private NavMeshPath path;
    private float elapsed = 0.0f;

    private void Start()
    {
        path = new NavMeshPath();
        elapsed = 0.0f;
    }


    void Update()
    {

        elapsed += Time.deltaTime;
        if (elapsed > 1.0f)
        {
            elapsed -= 1.0f;
            NavMesh.CalculatePath(Player.position, target.position, NavMesh.AllAreas, path);
            Path.positionCount = path.corners.Length;

            for (int i = 0; i < path.corners.Length; i++)
            {
                Path.SetPosition(i, path.corners[i] + Vector3.up * PathHeightOffset);
            }
        }
    }
        
}