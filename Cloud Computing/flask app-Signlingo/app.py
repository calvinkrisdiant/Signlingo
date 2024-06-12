from flask import Flask, request, jsonify
from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing.image import img_to_array
import numpy as np
from PIL import Image

app = Flask(__name__)

# Load your model
model = load_model("model/vgg16_model_v4_3epoch.h5")

# Define the class names from 'A' to 'Z'
class_names = [chr(i) for i in range(65, 91)]

# Threshold for confidence validation
CONFIDENCE_THRESHOLD = 0.9

def prepare_image(image, target_size=(224, 224)):
    # Convert the image to a PIL format
    if image.mode != 'RGB':
        image = image.convert('RGB')
    
    # Resize the image to the target size
    image = image.resize(target_size)
    
    # Convert the image to an array
    image = img_to_array(image)
    
    # Expand dimensions to match the model's input shape
    image = np.expand_dims(image, axis=0)
    
    # Normalize the image
    image = image / 255.0
    
    return image

@app.route('/predict', methods=['POST'])
def predict():
    # Check if an image file was uploaded
    if 'file' not in request.files:
        return jsonify({'error': 'No file provided'}), 400

    file = request.files['file']

    # Read the image
    try:
        image = Image.open(file.stream)
    except Exception as e:
        return jsonify({'error': 'Invalid image format'}), 400

    # Prepare the image
    image = prepare_image(image)
    
    # Predict the class
    predictions = model.predict(image)
    predicted_class_index = np.argmax(predictions, axis=1)[0]
    predicted_class = class_names[predicted_class_index]
    confidence = predictions[0][predicted_class_index]
    
    # Validate the prediction based on confidence
    if confidence < CONFIDENCE_THRESHOLD:
        return jsonify({'error': 'The image is not a valid hand sign language image'}), 400
    
    # Return the result as JSON
    return jsonify({
        'class': predicted_class,
        'confidence': float(confidence)
    })

if __name__ == '__main__':
    app.run(debug=True)
