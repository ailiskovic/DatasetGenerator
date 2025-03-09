import string

from pydantic import BaseModel

class RequestBody(BaseModel):
    rowNumbers: int
    rows: list

class IntegerElement:
    def __init__(self, type, name, increase, id, startValue, unitValue):
        self.type = type
        self.name = name
        self.increase = increase
        self.id = id
        self.startValue = startValue
        self.unitValue = unitValue

class IdElement:
    def __init__(self, type, name, increase, id, startValue):
        self.type = type
        self.name = name
        self.increase = increase
        self.id = id
        self.startValue = startValue

class FloatElement:
    def __init__(self, type, name, increase, id, startValue, unitValue):
        self.type = type
        self.name = name
        self.increase = increase
        self.id = id
        self.startValue = startValue
        self.unitValue = unitValue

class TimeStampElement:
    def __init__(self, type, name, increase, id, dateValue, timeValue, unitValue, timeUnit):
        self.type = type
        self.name = name
        self.increase = increase
        self.id = id
        #TODO: this type was DATE object, change this later
        self.dateValue = dateValue
        self.timeValue = timeValue
        self.unitValue = unitValue
        self.timeUnit = timeUnit

class NormalDistributionElement:
    def __init__(self, type, name, increase, id, muValue, sigmaValue):
        self.type = type
        self.name = name
        self.increase = increase
        self.id = id
        self.muValue = muValue
        self.sigmaValue = sigmaValue

class BinomialDistributionElement:
    def __init__(self, type, name, increase, id, nTrialsValue, pValue):
        self.type = type
        self.name = name
        self.increase = increase
        self.id = id
        self.nTrialsValue = nTrialsValue
        self.pValue = pValue

class CauchyDistributionElement:
    def __init__(self, type, name, increase, id, x0Value, lambdaValue):
        self.type = type
        self.name = name
        self.increase = increase
        self.id = id
        self.x0Value = x0Value
        self.lambdaValue = lambdaValue

class HawkessProcessElement:
    def __init__(self, type, name, id, lambdaValue, alphaValue, betaValue):
        self.type = type
        self.name = name
        self.id = id
        self.lambdaValue = lambdaValue
        self.alphaValue = alphaValue
        self.betaValue = betaValue

class MathElement:
    def __init__(self, type, name, id, formulaValue):
        self.type = type
        self.name = name
        self.id = id
        self.formulaValue = formulaValue

class RandomWalkNormalDistributionElement:
    def __init__(self, type, name, id, x0Value, muValue, sigmaValue):
        self.type = type
        self.name = name
        self.id = id
        self.x0Value = x0Value
        self.muValue = muValue
        self.sigmaValue = sigmaValue

class RandomWalkBinomialDistributionElement:
    def __init__(self, type, name, id, x0Value, nTrialsValue, pValue):
        self.type = type
        self.name = name
        self.id = id
        self.x0Value = x0Value
        self.nTrialsValue = nTrialsValue
        self.pValue = pValue

class RandomWalkCauchyDistributionElement:
    def __init__(self, type, name, id, x0Value, lambdaValue):
        self.type = type
        self.name = name
        self.id = id
        self.x0Value = x0Value
        self.lambdaValue = lambdaValue

