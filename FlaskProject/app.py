import numpy as np
from flask import Flask, jsonify, request
import json
import pickle
import plotly.express as px
import chart_studio.plotly as py
import pandas as pd


app = Flask(__name__)
model = pickle.load(open('model.pkl', 'rb'))

@app.route('/')
def home():
    return 'Welcome Home!'

@app.route('/predict/', methods = ['POST'])
def predict():
    if request.method == 'POST':
        decoded_data = request.data.decode('utf-8')
        params = json.loads(decoded_data)
        arr = []
        for data in params['x']:
            arr.append(int(data))
        data = np.array(arr)
        prediction = model.predict(data.reshape(-1, 1))
        print(prediction.tolist())
        visualize(data.tolist(), prediction.tolist())
        # output = round(prediction[0], 2)
        return jsonify({'output': prediction.tolist()})

def visualize(data, pred):
    df = pd.DataFrame({'Year':data, 'Customer':pred})
    fig = px.scatter(df, x='Year', y='Customer'
                 ,size='Customer'
                 , hover_data=['Year']
                 ,color= 'Year')
    fig.update_layout(template='plotly_white')
    fig.update_layout(title='Customer Prediction')
    # fig.show()
    py.plot(fig, filename = 'customer_vis', auto_open=False)

if __name__ == '__main__':
    app.run(debug=True)
