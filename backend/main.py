from datetime import datetime

import numpy as np
import pandas as pd
from dateutil.relativedelta import relativedelta
from scipy.stats import cauchy


from fastapi import FastAPI
from starlette.middleware.cors import CORSMiddleware

from classes import RequestBody, IntegerElement, IdElement, FloatElement, TimeStampElement, NormalDistributionElement, \
    BinomialDistributionElement, CauchyDistributionElement, HawkessProcessElement, MathElement, \
    RandomWalkNormalDistributionElement, RandomWalkBinomialDistributionElement, RandomWalkCauchyDistributionElement

app = FastAPI()

origins = ["*"]

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


@app.get("/")
def read_root():
    return ("test")


@app.post("/api/createNewDataset")
async def createNewDataset(request_body: RequestBody):
    numberOfRows, rowElements = processRequest(request_body)
    responseData = generateData(numberOfRows, rowElements)
    print(type(responseData))

    return responseData.to_csv(index=False)



def processRequest(body: RequestBody):
    print(body)
    rowNumber = body.rowNumbers
    rows = body.rows
    rowsElements = []
    for row in rows:
        if row.get('type') == 'Integer':
            rowsElements.append(createIntegerElement(row))
        elif row.get('type') == 'ID':
            rowsElements.append(createIdElement(row))
        elif row.get('type') == 'Float':
            rowsElements.append(createFloatElement(row))
        elif row.get('type') == 'Timestamp':
            rowsElements.append(createTimeStampElement(row))
        elif row.get('type') == 'Normal_Distribution':
            rowsElements.append(createNormalDistributionElement(row))
        elif row.get('type') == 'Binomial_Distribution':
            rowsElements.append(createBinomialDistributionElement(row))
        elif row.get('type') == 'Cauchy_Distribution':
            rowsElements.append(createCauchyDistributionElement(row))
        elif row.get('type') == 'Random_Walk_Normal_Distribution':
            rowsElements.append(createRandomWalkNormalDistributionElement(row))
        elif row.get('type') == 'Random_Walk_Binomial_Distribution':
            rowsElements.append(createRandomWalkBinomialDistributionElement(row))
        elif row.get('type') == 'Random_Walk_Cauchy_Distribution':
            rowsElements.append(createRandomWalkCauchyDistributionElement(row))
        elif row.get('type') == 'Math':
            rowsElements.append(createMathElement(row))
    return rowNumber, rowsElements

def generateData(numberOfRows, rowElements):
    data = pd.DataFrame()
    for rowElement in rowElements:
        if type(rowElement) is NormalDistributionElement:
            normalDistributionColumnValue = generateNormalDistributionValues(rowElement, numberOfRows)
            data[rowElement.name] = normalDistributionColumnValue
        if type(rowElement) is BinomialDistributionElement:
            binomialDistributionColumnValue = generateBinomialDistributionValues(rowElement, numberOfRows)
            data[rowElement.name] = binomialDistributionColumnValue
        if type(rowElement) is CauchyDistributionElement:
            cauchyDistributionColumnValue = generateCauchyDistributionValues(rowElement, numberOfRows)
            data[rowElement.name] = cauchyDistributionColumnValue
        if type(rowElement) is IdElement:
            idValues = generateIdValues(rowElement, numberOfRows)
            data[rowElement.name] = idValues
        if type(rowElement) is IntegerElement:
            integerValues = generateIntegerValues(rowElement, numberOfRows)
            data[rowElement.name] = integerValues
        if type(rowElement) is FloatElement:
            floatValues = generateFloatValues(rowElement, numberOfRows)
            data[rowElement.name] = floatValues
        if type(rowElement) is TimeStampElement:
            timeStampValues = generateTimeStampValues(rowElement, numberOfRows)
            data[rowElement.name] = timeStampValues
        if type(rowElement) is RandomWalkNormalDistributionElement:
            randomWalkNormalDistributionValues = generateRandomWalkNormalDistributionValues(rowElement, numberOfRows)
            data[rowElement.name] = randomWalkNormalDistributionValues
        if type(rowElement) is RandomWalkBinomialDistributionElement:
            randomWalkBinomialDistributionValues = generateRandomWalkBinomialDistributionValues(rowElement, numberOfRows)
            data[rowElement.name] = randomWalkBinomialDistributionValues
        if type(rowElement) is RandomWalkCauchyDistributionElement:
            randomWalkCauchyDistributionValues = generateRandomWalkCauchyDistributionValues(rowElement, numberOfRows)
            data[rowElement.name] = randomWalkCauchyDistributionValues
        if type(rowElement) is MathElement:
            data[rowElement.name] = generateMathValues(rowElement, numberOfRows, data)

    return data

def generateTimeStampValues(element: TimeStampElement, numberOfRows: int):
    dateTimeObject = parseDateString(element.dateValue, element.timeValue)
    dateTimeObject
    values = []
    for counter in range(numberOfRows):
        if counter != 0:
            if bool(element.increase):
                dateTimeObject = dateTimeObject + parseTildaValue(element.unitValue, counter, element.timeUnit)
            else:
                dateTimeObject = dateTimeObject - parseTildaValue(element.unitValue, counter, element.timeUnit)
        values.append(dateTimeObject.strftime("%Y-%m-%d %H:%M:%S.%f")[:-3])
    # increment or decrement using dt_plus_1_min = dt + timedelta(minutes=1)
    # timedelta

    return values

def parseTildaValue(unitValue: str, counter: int, timeUnit: str):
#     'year', 'month', 'day', 'hour', 'minute', 'second', 'milisecond'
    intUnitValue = int(unitValue)
    if timeUnit == 'year':
        return relativedelta(years=intUnitValue)
    elif timeUnit == 'month':
        return relativedelta(months=intUnitValue)
    elif timeUnit == 'day':
        return relativedelta(days=intUnitValue)
    elif timeUnit == 'hour':
        return relativedelta(hours=intUnitValue)
    elif timeUnit == 'minute':
        return relativedelta(minutes=intUnitValue)
    elif timeUnit == 'second':
        return relativedelta(seconds=intUnitValue)
    elif timeUnit == 'milisecond':
        return relativedelta(microseconds=(intUnitValue * 1000))
def parseDateString(dateString: str, timeString: str):
    input_string = dateString[:-1] if dateString.endswith('Z') else dateString
    dateTimeObject = datetime.fromisoformat(input_string)

    timeObject = datetime.strptime(timeString, "%H:%M:%S.%f")
    dateTimeObject = dateTimeObject.replace(hour=0, minute=0, second=0,
                           microsecond=0)
    dateTimeObject = dateTimeObject.replace(hour=timeObject.hour, minute=timeObject.minute, second=timeObject.second, microsecond=timeObject.microsecond)
    # dateTimeObject = dateTimeObject + datetime.timedelta(hour=timeObject.hour, minutes=timeObject.minute, seconds=timeObject.second, microseconds=timeObject.microsecond * 1000)
    return dateTimeObject


def generateFloatValues(element: IntegerElement, numberOfRows: int):
    values = []
    startValue = float(element.startValue)
    values.append(startValue)
    for i in range(numberOfRows - 1):
        if bool(element.increase):
            startValue = startValue + float(element.unitValue)
        else:
            startValue = startValue - float(element.unitValue)
        values.append(startValue)
    return values

def generateIntegerValues(element: IntegerElement, numberOfRows: int):
    values = []
    startValue = int(element.startValue)
    values.append(startValue)
    for i in range(numberOfRows - 1):
        if bool(element.increase):
            startValue = startValue + int(element.unitValue)
        else:
            startValue = startValue - int(element.unitValue)
        values.append(startValue)
    return values

def generateIdValues(element: IdElement, numberOfRows):
    values = []
    startValue = int(element.startValue)
    values.append(startValue)
    for i in range(numberOfRows - 1):
        if bool(element.increase):
            startValue = startValue + 1
        else:
            startValue = startValue - 1
        values.append(startValue)
    return values


def generateNormalDistributionValues(rowElement: NormalDistributionElement, numberOfRows):
    distribution = np.random.normal(float(rowElement.muValue), float(rowElement.sigmaValue), int(numberOfRows))
    return distribution

def generateBinomialDistributionValues(rowElement: BinomialDistributionElement, numberOfRows):
    distribution = np.random.binomial(int(rowElement.nTrialsValue), float(rowElement.pValue), int(numberOfRows));
    return distribution

def generateCauchyDistributionValues(rowElement: CauchyDistributionElement, numberOfRows):
    distribution = cauchy.rvs(loc=float(rowElement.x0Value), scale=float(rowElement.lambdaValue), size=int(numberOfRows))
    return distribution

def generateRandomWalkNormalDistributionValues(rowElement: RandomWalkNormalDistributionElement, numberOfRows):
    steps = np.random.normal(loc=float(rowElement.muValue), scale=float(rowElement.sigmaValue), size=int(numberOfRows))
    directions = np.random.choice([-1, 1], size=len(steps))
    randomSteps = steps * directions
    randomWalk = float(rowElement.x0Value) + np.cumsum(randomSteps)
    return randomWalk

def generateRandomWalkBinomialDistributionValues(rowElement: RandomWalkBinomialDistributionElement, numberOfRows):
    steps = np.random.binomial(int(rowElement.nTrialsValue), float(rowElement.pValue), int(numberOfRows))
    directions = np.random.choice([-1, 1], size=len(steps))
    randomSteps = steps * directions
    randomWalk = float(rowElement.x0Value) + np.cumsum(randomSteps)
    return randomWalk

def generateRandomWalkCauchyDistributionValues(rowElement: CauchyDistributionElement, numberOfRows):
    steps = cauchy.rvs(loc=float(rowElement.x0Value), scale=float(rowElement.lambdaValue), size=int(numberOfRows))
    directions = np.random.choice([-1, 1], size=len(steps))
    randomSteps = steps * directions
    randomWalk = float(rowElement.x0Value) + np.cumsum(randomSteps)
    return randomWalk

def generateMathValues(rowElement: MathElement, numberOfRows, data):
    try:
        result = data.eval(str(rowElement.formulaValue))
    except Exception as e:
        print(f"Error evaluating formula {str(rowElement.formulaValue)}: {e}")
        result = pd.Series([None] * len(data))

    return result

def createIntegerElement(element):
    type = element.get('type')
    name = element.get('name')
    increase = element.get('increase')
    id = element.get('id')
    startValue = element.get('startValue')
    unitValue = element.get('unitValue')
    return IntegerElement(type, name, increase, id, startValue, unitValue)

def createFloatElement(element):
    type = element.get('type')
    name = element.get('name')
    increase = element.get('increase')
    id = element.get('id')
    startValue = element.get('startValue')
    unitValue = element.get('unitValue')
    return FloatElement(type, name, increase, id, startValue, unitValue)

def createIdElement(element):
    type = element.get('type')
    name = element.get('name')
    increase = element.get('increase')
    id = element.get('id')
    startValue = element.get('startValue')
    return IdElement(type, name, increase, id, startValue)

def createTimeStampElement(element):
    type = element.get('type')
    name = element.get('name')
    increase = element.get('increase')
    id = element.get('id')
    dateValue = element.get('dateValue')
    timeValue = element.get('timeValue')
    unitValue = element.get('unitValue')
    timeUnit = element.get('timeUnit')
    return TimeStampElement(type, name, increase, id, dateValue, timeValue, unitValue, timeUnit)

def createNormalDistributionElement(element):
    type = element.get('type')
    name = element.get('name')
    increase = element.get('increase')
    id = element.get('id')
    muValue = element.get('muValue')
    sigmaValue = element.get('sigmaValue')
    return NormalDistributionElement(type, name, increase, id, muValue, sigmaValue)

def createBinomialDistributionElement(element):
    type = element.get('type')
    name = element.get('name')
    increase = element.get('increase')
    id = element.get('id')
    nTrialsValue = element.get('nTrialsValue')
    pValue = element.get('pValue')
    return BinomialDistributionElement(type, name, increase, id, nTrialsValue, pValue);

def createCauchyDistributionElement(element):
    type = element.get('type')
    name = element.get('name')
    increase = element.get('increase')
    id = element.get('id')
    x0Value = element.get('x0Value')
    lambdaValue = element.get('lambdaValue')
    return CauchyDistributionElement(type, name, increase, id, x0Value, lambdaValue)

def createHawkessProcessElement(element):
    type = element.get('type')
    name = element.get('name')
    id = element.get('id')
    lambdaValue = element.get('lambdaValue')
    alphaValue = element.get('alphaValue')
    betaValue = element.get('betaValue')
    return HawkessProcessElement(type, name, id, lambdaValue, alphaValue, betaValue)

def createMathElement(element):
    type = element.get('type')
    name = element.get('name')
    id = element.get('id')
    formulaValue = element.get('formulaValue')
    return MathElement(type, name, id, formulaValue)

def createRandomWalkNormalDistributionElement(element):
    type = element.get('type')
    name = element.get('name')
    id = element.get('id')
    x0Value = element.get('x0Value')
    muValue = element.get('muValue')
    sigmaValue = element.get('sigmaValue')
    return RandomWalkNormalDistributionElement(type, name, id, x0Value, muValue, sigmaValue)

def createRandomWalkBinomialDistributionElement(element):
    type = element.get('type')
    name = element.get('name')
    id = element.get('id')
    x0Value = element.get('x0Value')
    nTrialsValue = element.get('nTrialsValue')
    pValue = element.get('pValue')
    return RandomWalkBinomialDistributionElement(type, name, id, x0Value, nTrialsValue, pValue)

def createRandomWalkCauchyDistributionElement(element):
    type = element.get('type')
    name = element.get('name')
    id = element.get('id')
    x0Value = element.get('x0Value')
    lambdaValue = element.get('lambdaValue')
    return RandomWalkCauchyDistributionElement(type, name, id, x0Value, lambdaValue)