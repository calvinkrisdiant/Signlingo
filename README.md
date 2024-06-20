# Signlingo - Sign Language Learning Application

![Logo Signlingo](https://github.com/calvinkrisdiant/Signlingo/blob/6c5e7ceb1d23a4918bdf33c8e93c595892a16fce/Logo-%20Signlingo.png)

Signlingo is an application designed for learning sign language, developed as the final project or capstone by team C241-PS420 for the Bangkit 2024 program. The application aims to facilitate interactive and enjoyable sign language learning.

## Signlingo Team (C241-PS420)

|            Name             | Bangkit-ID |         Path         |
| :-------------------------: | :--------: | :------------------: |
|   Satrio Onto Wiryo          | M279D4KY2181 |  Machine Learning   |
|   Kresna Fajri Wicaksana     | M002D4KY2649 |  Machine Learning   |
|   Shafida Afifah Firdausy    | M279D4KX3233 |  Machine Learning   |
|   Muhammad Calvin Krisdianto  | C258D4KY0731 |  Cloud Computing    |
|   Ahmad Febrian Dzulfikar     | C258D4KY0621 |  Cloud Computing    |
|   Muhammad Ridho              | A545D4NY4630 |  Mobile Development |
|   Aisha Dwi Anindita Radianto | A545D4NX4631 |  Mobile Development |

## Tech Stack

![Tech Stack](https://github.com/calvinkrisdiant/Signlingo/blob/2d10efe3c965581a34e4e32f51d0d6036e14ce52/tech-stack.png)

## Dataset

- [Dataset Bahasa Isyarat](https://github.com/shafidaaaa/Bangkit/tree/main/Capstone/bisindo_data)

## Prototype

The application prototype can be viewed [here]([https://www.figma.com/file/prototype-link](https://www.figma.com/design/sIQCAepbc4Z4DLoxxrLxob/SignLingo-(Capstone-Project)?node-id=0-1&t=xI0Z9fvWafH3Xbnw-1)).

## Mobile Development Learning Path

## Machine Learning Path

The model building is in [here](https://github.com/calvinkrisdiant/Signlingo/blob/main/Machine%20Learning/Model/bisindo_final.ipynb)
### Requirements to build this model
1. NumPy version: 1.26.4
2. TensorFlow version: 2.16.1
3. Keras version: 3.3.3
4. sklearn version: 1.5.0
5. matplotlib version: 3.9.0

### Pre-Processing
1. Clone the GitHub link dataset above
2. Loads images from multiple directories
3. Combines them into a single dataset (X)
4. Collect corresponding labels (y) from the folder name of each file
5. Resize each image to a standard size (64x64 pixels)
6. Converts images to numerical arrays and normalizes pixel values (0 to 1)
7. Encodes labels into numerical format
8. Splits data into training and test sets
9. Training set (80%) and test set (20%)
10. Build the model with the architecture below
11. Evaluate model performance on test set
12. Tuning the hyperparameter and iterating the process based on the results
13. Export to keras model with format h5

### Model architecture
<img src="https://github.com/calvinkrisdiant/Signlingo/blob/main/Machine%20Learning/stock/modelNEW.v.1.h5_page-0001.jpg" alt="5" width="auto" height="900"> 
<br>

### Model Accuracy
The model reached 97% accuracy on the training set and 94% accuracy on the test set
<img src="https://github.com/calvinkrisdiant/Signlingo/blob/main/Machine%20Learning/Model/training_plot.png" alt="6" width="auto" height="300"> 
<br>

### Confusion Matrix
<img src="https://github.com/calvinkrisdiant/Signlingo/blob/main/Machine%20Learning/Model/conf_matrix.png" alt="7" width="auto" height="300"> 
<br>

### Sample of model prediction on the test set
<img src="https://github.com/calvinkrisdiant/Signlingo/blob/main/Machine%20Learning/Model/X_test.png" alt="8" width="auto" height="300"> 
<br>

### Model Deployment
using Keras model format and Flask<br>
```model.save('final_model.h5')```<br>
[Flask deploy](https://github.com/calvinkrisdiant/Signlingo/blob/main/Machine%20Learning/Model/app.py)

## Cloud Computing Learning Path

### Steps To Deploy Backend API Login Register To Cloud run
1. Clone this repository to Google Cloud Shell
2. Open editor and move to this folder login-register-api
3. Activated Cloud SQL Admin API
4. Create database in Cloud SQL
5. Run this command <i>npm install</i>
6. Change the configuration in config.js, .env, Dockerfile with new configuration
7. Deploy your app to Cloud Run with this command <i>gcloud init</i> after that <i>gcloud run deploy --image gcr.io/PROJECT_ID/IMAGE_NAME --platform managed</i>

### Steps To Deploy Model H5 To Cloud run
1. Clone this repository to Google Cloud Shell
2. Open editor and move to this folder flaskapp-signlingo
3. Dockerize Flask Application
4. Build and Push the Docker Image
5. Change the configuration in Dockerfile with new configuration
6. Deploy your app to Cloud Run with this command <i>gcloud init</i> after that <i>gcloud run deploy --image gcr.io/<PROJECT_ID>/flask-app --platform managed --allow-unauthenticated</i>

### Steps To Deploy Model H5 To Cloud run
1. Clone this repository to Google Cloud Shell
2. Open editor and move to this folder landing-page
3. Dockerize Flask Application
4. Build and Push the Docker Image
5. Change the configuration in Dockerfile with new configuration
6. Deploy your app to Cloud Run with this command <i>gcloud init</i> after that <i>gcloud run deploy --image gcr.io/<PROJECT_ID>/landing-page --platform managed --allow-unauthenticated</i>

### Cloud Architecture
1. API Register & Login <br>
<img src="https://github.com/calvinkrisdiant/Signlingo/blob/e6ea0b92872461f8f7bc52ae56be55fe9b1c898c/Cloud%20Computing/Login-register.jpg" alt="4" width="auto" height="300"> <br><br>
2. API Prediction <br>
<img src="https://github.com/calvinkrisdiant/Signlingo/blob/e6ea0b92872461f8f7bc52ae56be55fe9b1c898c/Cloud%20Computing/Prediction.jpg" alt="4" width="auto" height="300"> <br><br>
2. Signlingo Landing Page <br>
<img src="https://github.com/calvinkrisdiant/Signlingo/blob/7bb2f5bb187fcee02dbf16016acd97955eac7699/Cloud%20Computing/Landing-Page.png" alt="4" width="auto" height="300"> <br><br>

### Featured Technologies
* [Google Cloud Platform](https://cloud.google.com/)
* [Bootsrap](https://getbootstrap.com/)



## Instalasi

numpy==1.26.4
tensorFlow==.16.1
keras==3.3.3
sklearn==1.5.0
matplotlib==3.9.0
Untuk menjalankan aplikasi ini, pastikan Anda telah melakukan instalasi semua dependensi yang diperlukan.

