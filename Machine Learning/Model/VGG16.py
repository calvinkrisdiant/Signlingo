# import os
# import numpy as np
# import tensorflow as tf
# from tensorflow.keras.applications import VGG16
# from tensorflow.keras.preprocessing.image import load_img, img_to_array
# from sklearn.preprocessing import LabelEncoder
# from sklearn.model_selection import train_test_split
# from tensorflow.keras.models import Sequential
# from tensorflow.keras.layers import Dense, GlobalAveragePooling2D
# from tensorflow.keras.preprocessing.image import ImageDataGenerator

# image_directory1 = "D:/Bangkit/Capstone/data/Citra BISINDO"
# image_directory2 = "D:/Bangkit/Capstone/data/bisindo_dataset_v2"
# image_directory3 = "D:/Bangkit/Capstone/data/bisindo_dataset_v3"

# BATCH_SIZE = 4
# IMAGE_SIZE = (224, 224)

# # Load images and labels
# def load_images_and_labels(image_directory, image_size=IMAGE_SIZE, batch_size=BATCH_SIZE):
#     images = []
#     labels = []
#     for label in os.listdir(image_directory):
#         label_path = os.path.join(image_directory, label)
#         if os.path.isdir(label_path):
#             image_paths = [os.path.join(label_path, image_name) for image_name in os.listdir(label_path)]
#             for batch_start in range(0, len(image_paths), batch_size):
#                 batch_end = min(batch_start + batch_size, len(image_paths))
#                 batch_image_paths = image_paths[batch_start:batch_end]
#                 batch_images = []
#                 for image_path in batch_image_paths:
#                     image = tf.keras.preprocessing.image.load_img(image_path, target_size=image_size)
#                     image = tf.keras.preprocessing.image.img_to_array(image)
#                     image = image / 255.0  # Normalize the image
#                     # image = (image / 127.5) - 1.0 # Normalize and rescale
#                     batch_images.append(image)
#                 images.extend(batch_images)
#                 labels.extend([label] * len(batch_images))
#     return np.array(images), np.array(labels)

# # Load images and labels
# X1, y1 = load_images_and_labels(image_directory1)
# X2, y2 = load_images_and_labels(image_directory2)
# X3, y3 = load_images_and_labels(image_directory3)

# # Combine the data
# X = np.concatenate((X1, X2, X3), axis=0)
# y = np.concatenate((y1, y2, y3), axis=0)

# # Encode labels
# label_encoder = LabelEncoder()
# y_encoded = label_encoder.fit_transform(y)
# y_categorical = tf.keras.utils.to_categorical(y_encoded)

# # Split into training and test sets
# X_train, X_test, y_train, y_test = train_test_split(X, y_categorical, test_size=0.2, random_state=42)

# # Create an ImageDataGenerator for data augmentation
# datagen = ImageDataGenerator(
#     rescale=1.0/255.0,
#     rotation_range=20,
#     width_shift_range=0.2,
#     height_shift_range=0.2,
#     shear_range=0.2,
#     zoom_range=0.2,
#     horizontal_flip=True,
#     fill_mode='nearest'
# )

# # Load VGG16 model pre-trained on ImageNet
# base_model = VGG16(weights='imagenet', include_top=False, input_shape=(224, 224, 3))

# # Freeze the layers of the base model
# for layer in base_model.layers:
#     layer.trainable = False

# # Create a new model that includes both the VGG16 base model and the classifier
# model = Sequential([
#     base_model,
#     # GlobalAveragePooling2D(),
#     Dense(512, activation='relu'),
#     Dense(len(label_encoder.classes_), activation='softmax')
# ])

# # Compile the model
# model.compile(optimizer='adam', loss='categorical_crossentropy', metrics=['accuracy'])

# # Train the model using data generators
# train_generator = datagen.flow(X_train, y_train, batch_size=BATCH_SIZE)
# test_generator = datagen.flow(X_test, y_test, batch_size=BATCH_SIZE)

# # Define callbacks
# early_stopping = keras.callbacks.EarlyStopping(monitor='val_loss', patience=5, restore_best_weights=True)
# model_checkpoint = keras.callbacks.ModelCheckpoint('best_model.keras', save_best_only=True)

# # Train the model
# history = model.fit(train_generator, 
#                     #steps_per_epoch=10,
#                     epochs=50, 
#                     validation_data=test_generator ,
#                     #validation_steps=40,
#                     callbacks=[early_stopping, model_checkpoint]
# )
# # Reset the generators before evaluation and prediction
# test_generator.reset()

# # Evaluate the model
# # test_loss, test_acc = model.evaluate(test_generator, validation_steps=80)
# model.save('vgg16_model.v.3.h5')

# # Make predictions
# predictions = model.predict(test_generator)

# # Get the predicted classes
# predicted_classes = np.argmax(predictions, axis=1)
# predicted_class = label_encoder.inverse_transform(predicted_classes)

# # Get the true classes
# true_classes = np.argmax(y_test, axis=1)
# true_class = label_encoder.inverse_transform(true_classes)

# # Print some predictions and their corresponding true classes
# print("Predicted classes :", predicted_class[:10])
# print("True classes      :", true_class[:10])


### BATAS VGG BELOW IS NOT USING IMAGEDATAGENERATOR
import os
import numpy as np
import tensorflow as tf
from tensorflow.keras.applications import VGG16
from tensorflow.keras.preprocessing.image import load_img, img_to_array
from sklearn.preprocessing import LabelEncoder
from sklearn.model_selection import train_test_split
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, GlobalAveragePooling2D, Flatten
from tensorflow.keras.callbacks import EarlyStopping, ModelCheckpoint

image_directory1 = "D:/Bangkit/Capstone/data/Citra BISINDO"
image_directory2 = "D:/Bangkit/Capstone/data/bisindo_dataset_v2"
image_directory3 = "D:/Bangkit/Capstone/data/bisindo_dataset_v3"

BATCH_SIZE = 4
IMAGE_SIZE = (224, 224)

# Load images and labels
def load_images_and_labels(image_directory, image_size=IMAGE_SIZE, batch_size=BATCH_SIZE):
    images = []
    labels = []
    for label in os.listdir(image_directory):
        label_path = os.path.join(image_directory, label)
        if os.path.isdir(label_path):
            image_paths = [os.path.join(label_path, image_name) for image_name in os.listdir(label_path)]
            for batch_start in range(0, len(image_paths), batch_size):
                batch_end = min(batch_start + batch_size, len(image_paths))
                batch_image_paths = image_paths[batch_start:batch_end]
                batch_images = []
                for image_path in batch_image_paths:
                    image = tf.keras.preprocessing.image.load_img(image_path, target_size=image_size)
                    image = tf.keras.preprocessing.image.img_to_array(image)
                    image = image / 255.0  # Normalize the image
                    batch_images.append(image)
                images.extend(batch_images)
                labels.extend([label] * len(batch_images))
    return np.array(images), np.array(labels)

# Load images and labels
X1, y1 = load_images_and_labels(image_directory1)
X2, y2 = load_images_and_labels(image_directory2)
X3, y3 = load_images_and_labels(image_directory3)

# Combine the data
X = np.concatenate((X1, X2, X3), axis=0)
y = np.concatenate((y1, y2, y3), axis=0)

# Encode labels
label_encoder = LabelEncoder()
y_encoded = label_encoder.fit_transform(y)
y_categorical = tf.keras.utils.to_categorical(y_encoded)

# Split into training and test sets
X_train, X_test, y_train, y_test = train_test_split(X, y_categorical, test_size=0.2, random_state=42)

# Load VGG16 model pre-trained on ImageNet
base_model = VGG16(weights='imagenet', include_top=False, input_shape=(224, 224, 3))

# Freeze the layers of the base model
for layer in base_model.layers:
    layer.trainable = False

# Create a new model that includes both the VGG16 base model and the classifier
model = Sequential([
    base_model,
    Flatten(),
    Dense(512, activation='relu'),
    Dense(len(label_encoder.classes_), activation='softmax')
])

# Compile the model
model.compile(optimizer='adam', loss='categorical_crossentropy', metrics=['accuracy'])

# Define callbacks
early_stopping = EarlyStopping(monitor='val_loss', patience=5, restore_best_weights=True)
model_checkpoint = ModelCheckpoint('best_model.keras', save_best_only=True)

# Train the model
history = model.fit(X_train, y_train, 
                    batch_size=BATCH_SIZE, 
                    epochs=3, 
                    validation_data=(X_test, y_test),
                    callbacks=[early_stopping, model_checkpoint]
)

# Save the model
model.save('vgg16_model_v4.10epoch.h5')

# Evaluate the model
test_loss, test_acc = model.evaluate(X_test, y_test)
print(f"Test accuracy: {test_acc:.4f}")

# Make predictions
predictions = model.predict(X_test)

# Get the predicted classes
predicted_classes = np.argmax(predictions, axis=1)
predicted_class = label_encoder.inverse_transform(predicted_classes)

# Get the true classes
true_classes = np.argmax(y_test, axis=1)
true_class = label_encoder.inverse_transform(true_classes)

# Print some predictions and their corresponding true classes
print("Predicted classes:", predicted_class[:10])
print("True classes     :", true_class[:10])
