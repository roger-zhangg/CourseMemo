using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Book : MonoBehaviour
{   
    public AudioClip impact;
    public AudioClip findMe;
    public AudioClip foundMe;
    public AudioClip youAgain;
    public AudioClip pickFlower;
    public AudioClip flowerEnd;
    public AudioClip pickMushroom;
    public AudioClip backTo;
    public AudioClip finish;

    AudioSource audioSource;
    float degreesPerSecond = 10;
    // Start is called before the first frame update
    Vector3 startPoint = new Vector3(351,24,72);
    Vector3 alteriaPoint = new Vector3(483,22,351);
    Vector3 flowerPoint = new Vector3(397,22,275);
    Vector3 mushroomPoint = new Vector3(358,22,279);
    private float stage0Elapsed = 0.0f;
    private float stage1Elapsed = 0.0f;
    private float stage2Elapsed = 0.0f;
    private float stage2Elapsed2 = 0.0f;
    private float stage3Elapsed = 0.0f;
    private float stage3Elapsed2 = 0.0f;
    private float stage4Elapsed = 0.0f;
    private float stage5Elapsed = 0.0f;
    //Vector3 alteriaPoint = new Vector3(351,23,72);
    void Start()
    {

        transform.position = startPoint;
        audioSource = GetComponent<AudioSource>();
    }

    // Update is called once per frame
    void Update()
    {
        if (Score.stage == 0){
            stage0Elapsed+= Time.deltaTime;
            if (stage0Elapsed > 10.0f){
                audioSource.PlayOneShot(findMe,1.0f);
                stage0Elapsed = -20.0f;
            }
        }
        if (Score.stage == 1){
            if (stage1Elapsed == 0.0f){
                audioSource.PlayOneShot(foundMe,1.0f);
            }
            stage1Elapsed+= Time.deltaTime;
            
            if (stage1Elapsed > 15.0f){
                transform.position = alteriaPoint;
            }
        }
        if (Score.stage == 2){
            if (stage2Elapsed == 0.0f){
                audioSource.PlayOneShot(youAgain,1.0f);
            }
            stage2Elapsed+= Time.deltaTime;
            if (stage2Elapsed > 15.0f){
                transform.position = flowerPoint;
                if (stage2Elapsed > 45.0f){
                    audioSource.PlayOneShot(pickFlower,1.0f);
                    stage2Elapsed = 16.0f;
                }
            }
        }
        if (Score.stage == 3){
            stage3Elapsed+= Time.deltaTime;
            transform.position = mushroomPoint;
            if (stage3Elapsed > 15.0f){
                if (stage3Elapsed > 45.0f){
                    audioSource.PlayOneShot(pickMushroom,1.0f);
                    stage2Elapsed = 16.0f;
                }
            }
        }
        if (Score.stage == 4){
            transform.position = alteriaPoint;
        }
        if (Score.stage == 5){
            if (stage5Elapsed == 0.0f){
                audioSource.PlayOneShot(finish,1.0f);
            }
            stage5Elapsed+= Time.deltaTime;
        }
        if (Score.flower>=1){
            if (Score.stage == 2){
                stage2Elapsed2+= Time.deltaTime;
                if (stage2Elapsed2 > 15.0f){
                    if(stage2Elapsed2 <20.0f){
                        audioSource.PlayOneShot(flowerEnd,1.0f);
                        stage2Elapsed2 = 20.0f;
                    }
                }
                if (stage2Elapsed2 > 30.0f){
                    Score.stage++;
                }
            }
        }
        if (Score.mushroom>=1){
            if (Score.stage == 3){
                stage3Elapsed2+= Time.deltaTime;
                if (stage3Elapsed2 > 15.0f){
                    if(stage3Elapsed2 <20.0f){
                        audioSource.PlayOneShot(backTo,1.0f);
                        stage3Elapsed2 = 20.0f;
                    }
                }
                if (stage3Elapsed2 > 30.0f){
                    Score.stage++;
                }
            }
        }
        transform.Rotate(new Vector3(0, degreesPerSecond, 0) * Time.deltaTime);
    }

    private void OnTriggerEnter(Collider other)
    {
        
        if(other.gameObject.CompareTag("flower")){
            audioSource.PlayOneShot(impact,0.7f);
            Score.flower++;
            other.gameObject.SetActive(false);
        }
        if(other.gameObject.CompareTag("mushroom")){
            audioSource.PlayOneShot(impact,0.7f);
            Score.mushroom++;
            other.gameObject.SetActive(false);
        }

        
    }


}
