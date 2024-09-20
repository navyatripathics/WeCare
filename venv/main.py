from flask import Flask, request, render_template, jsonify
import numpy as np
import pandas as pd
import pickle


# Flask app initialization
app = Flask(__name__)

# Load datasets
try:
    sym_des = pd.read_csv("datasets/symtoms_df.csv")
    precautions = pd.read_csv("datasets/precautions_df.csv")
    workout = pd.read_csv("datasets/workout_df.csv")
    description = pd.read_csv("datasets/description.csv")
    medications = pd.read_csv('datasets/medications.csv')
    diets = pd.read_csv("datasets/diets.csv")
except FileNotFoundError as e:
    print(f"Error loading files: {e}")

# Load model
try:
    svc = pickle.load(open('models/svc.pkl','rb'))
except FileNotFoundError as e:
    print(f"Error loading model: {e}")

# Helper function to retrieve details for a predicted disease
def helper(dis):
    desc = description[description['Disease'] == dis]['Description']
    desc = " ".join([w for w in desc])

    pre = precautions[precautions['Disease'] == dis][['Precaution_1', 'Precaution_2', 'Precaution_3', 'Precaution_4']]
    pre = [col for col in pre.values]

    med = medications[medications['Disease'] == dis]['Medication']
    med = [med for med in med.values]

    die = diets[diets['Disease'] == dis]['Diet']
    die = [die for die in die.values]

    wrkout = workout[workout['disease'] == dis]['workout']

    return desc, pre, med, die, wrkout

# Mapping of symptoms to their respective indices
symptoms_dict = { ... }  # Same as in the original code

# Mapping of diseases based on the model's output
diseases_list = { ... }  # Same as in the original code

# Model prediction function

