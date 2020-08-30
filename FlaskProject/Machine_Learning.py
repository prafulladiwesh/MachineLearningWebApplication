#!/usr/bin/env python
# coding: utf-8

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import pickle
import sys

data = pd.read_csv('xyzairlines_data.csv')

from sklearn.model_selection import train_test_split
from sklearn.linear_model import LinearRegression
from sklearn import metrics


X = data['Year'].values
y = data['Number of Customers'].values
data = data.drop(['Name'], axis=1)
to_pred = np.array([2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025])


def regressor(X, y, to_pred):
    lr = LinearRegression()
    lr.fit(X.reshape(-1, 1), y)
    pickle.dump(lr, open('model.pkl','wb'))
    model = pickle.load(open('model.pkl','rb'))
    return model.predict(to_pred.reshape(-1, 1))


print(regressor(X, y, to_pred))



