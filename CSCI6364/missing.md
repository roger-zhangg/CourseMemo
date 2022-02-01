| column                                                       | fix                                       |
| ------------------------------------------------------------ | ----------------------------------------- |
| Are you  self-employed?                                      | ok                                        |
| How many employees does your company or  organization have?  | some missing, map from 0-6, missing as 0  |
| Is your employer primarily a tech  company/organization?     | missing some, set default to 0            |
| Is your primary role within your company  related to tech/IT? | missing a lot, drop this feature          |
| Does your employer provide mental health  benefits as part of healthcare coverage? | merge to access to mental health          |
| Do you know the options for mental health  care available under your employer-provided coverage? | merge to access to mental health          |
| Has your employer ever formally discussed  mental health (for example, as part of a wellness campaign or other official  communication)? | merge to access to mental health          |
| Does your employer offer resources to  learn more about mental health concerns and options for seeking help? | merge to access to mental health          |
| Is your anonymity protected if you choose  to take advantage of mental health or substance abuse treatment resources  provided by your employer? | merge to access to mental health          |
| If a mental health issue prompted you to  request a medical leave from work, asking for that leave would be:Do you  think that discussing a mental health disorder with your employer would have  negative consequences? | merge to access to mental health          |
| Do you think that discussing a physical  health issue with your employer would have negative consequences? | merge to access to mental health          |
| Would you feel comfortable discussing a  mental health disorder with your coworkers? | merge to access to mental health          |
| Would you feel comfortable discussing a  mental health disorder with your direct supervisor(s)? | merge to access to mental health          |
| Do you feel that your employer takes  mental health as seriously as physical health? | merge to access to mental health          |
| Have you heard of or observed negative  consequences for co-workers who have been open about mental health issues in  your workplace? | merge to access to mental health          |
| Do you have medical coverage (private  insurance or state-provided) which includes treatment of Â mental health  issues? | drop ,too many missing                    |
| Do you know local or online resources to  seek help for a mental health disorder? | drop ,too many missing                    |
| If you have been diagnosed or treated for  a mental health disorder, do you ever reveal this to clients or business  contacts? | drop ,too many missing                    |
| If you have revealed a mental health  issue to a client or business contact, do you believe this has impacted you  negatively? | drop ,too many missing                    |
| If you have been diagnosed or treated for  a mental health disorder, do you ever reveal this to coworkers or employees? | drop ,too many missing                    |
| If you have revealed a mental health  issue to a coworker or employee, do you believe this has impacted you  negatively? | drop ,too many missing                    |
| Do you believe your productivity is ever  affected by a mental health issue? | drop ,too many missing                    |
| If yes, what percentage of your work time  (time performing primary or secondary job functions) is affected by a mental  health issue? | drop ,too many missing                    |
| Do you have previous employers?                              | 0/1,keep                                  |
| Have your previous employers provided  mental health benefits? | merge to previous access to mental health |
| Were you aware of the options for mental  health care provided by your previous employers? | merge to previous access to mental health |
| Did your previous employers ever formally  discuss mental health (as part of a wellness campaign or other official  communication)? | merge to previous access to mental health |
| Did your previous employers provide  resources to learn more about mental health issues and how to seek help? | merge to previous access to mental health |
| Was your anonymity protected if you chose  to take advantage of mental health or substance abuse treatment resources  with previous employers? | merge to previous access to mental health |
| Do you think that discussing a mental  health disorder with previous employers would have negative consequences? | merge to previous access to mental health |
| Do you think that discussing a physical  health issue with previous employers would have negative consequences? | merge to previous access to mental health |
| Would you have been willing to discuss a  mental health issue with your previous co-workers? | merge to previous access to mental health |
| Would you have been willing to discuss a  mental health issue with your direct supervisor(s)? | merge to previous access to mental health |
| Did you feel that your previous employers  took mental health as seriously as physical health? | merge to previous access to mental health |
| Did you hear of or observe negative  consequences for co-workers with mental health issues in your previous  workplaces? | merge to previous access to mental health |
| Would you be willing to bring up a  physical health issue with a potential employer in an interview? | drop                                      |
| Why or why not?                                              | drop                                      |
| Would you bring up a mental health issue  with a potential employer in an interview? | merge to attitude toward mental illness   |
| Why or why not?                                              | drop                                      |
| Do you feel that being identified as a  person with a mental health issue would hurt your career? | merge to attitude toward mental illness   |
| Do you think that team members/co-workers  would view you more negatively if they knew you suffered from a mental health  issue? | merge to attitude toward mental illness   |
| How willing would you be to share with  friends and family that you have a mental illness? | merge to attitude toward mental illness   |
| Have you observed or experienced an  unsupportive or badly handled response to a mental health issue in your  current or previous workplace? | merge to attitude toward mental illness   |
| Have your observations of how another  individual who discussed a mental health disorder made you less likely to  reveal a mental health issue yourself in your current workplace? | merge to attitude toward mental illness   |
| Do you have a family history of mental  illness?             | merge to                                  |
| Have you had a mental health disorder in  the past?          | Yes/no/maybe -> change to 1,0,0.5         |
| Do you currently have a mental health  disorder?             | Yes/no/maybe -> change to 1,0,0.5         |
| If yes, what condition(s) have you been  diagnosed with?     | drop                                      |
| If maybe, what condition(s) do you  believe you have?        | drop                                      |
| Have you been diagnosed with a mental  health condition by a medical professional? | Yes/no                                    |
| If so, what condition(s) were you  diagnosed with?           | drop, custom column                       |
| Have you ever sought treatment for a  mental health issue from a mental health professional? | 1/0                                       |
| If you have a mental health issue, do you  feel that it interferes with your work when being treated effectively? | drop                                      |
| If you have a mental health issue, do you  feel that it interferes with your work when NOT being treated effectively? | drop                                      |
| What is your age?                                            | no missing int                            |
| What is your gender?                                         | map to is_male, is_female.                |
| What country do you live in?                                 | drop, high cardinality                    |
| What country do you work in?                                 | drop, high cardinality                    |
| What US state or  territory do you work in?                  | drop, high cardinality                    |
| Which of the following best describes your work position?    | drop, high cardinality                    |
| Do you work remotely?                                        | sometimes ->1, always->2, never->0        |

