package com.example.datasetgeneratiorapp.CreateNewDataset;

import com.example.datasetgeneratiorapp.Utils.Elements.*;
import com.example.datasetgeneratiorapp.Utils.Util;
import com.opencsv.CSVWriter;
import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * Controller class for The application
 *
 */
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CreateNewDatasetController {

    /**
     *
     * Method which generates the dataset
     *
     * @param body - body part of the request
     * @param response - response to the request
     * @return - generated dataset file
     */
    @RequestMapping(value = "/api/createNewDataset", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public FileSystemResource generate(@RequestBody String body, HttpServletResponse response)
    {

        JSONObject jsonObject = getObject(body);
        int numberOfRows = getNumberOfRows(jsonObject);
        JSONArray rows = (JSONArray) jsonObject.get("rows");
        ArrayList<Element> elements = getRowsElements(rows);
        int numberOfColumns = rows.length();
        List<String[]> dataList = generateInputData(elements, numberOfRows, numberOfColumns);
        // the naming of the file is done at frontend part
        File file = new File("test.csv");
        try {
            FileWriter outputfile = new FileWriter(file);

            CSVWriter writer = new CSVWriter(outputfile, ',',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
            writer.writeAll(dataList);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        response.setHeader("Content-Disposition", "attachment; filename=" + "test.csv");
        return new FileSystemResource("test.csv");
    }


    /**
     *
     * Method which generate values for the dataset
     *
     * @param elements - elements of the request, e.g. Integer, Math, ...
     * @param numberOfRows - number of rows requested
     * @param numberOfColumns - number of culumns requested
     * @return - list of generated values for the file
     */
    public List<String[]>  generateInputData(ArrayList<Element> elements, int numberOfRows, int numberOfColumns)
    {
        List<String[]> listOfData = new ArrayList<>();
        List<String> headerList = new ArrayList<>();

        Random random = new Random();

        for(int i = 0; i < numberOfColumns; i++)
        {
          headerList.add(elements.get(i).getName());
        }
        String[] headerData = headerList.toArray(new String[0]);
        listOfData.add(headerData);

        for (int i = 0; i < numberOfRows; i++)
        {
            List<String> rowList = new ArrayList<>();

            for (int j = 0; j < numberOfColumns; j++) {
                Element currentElement = elements.get(j);
                if (currentElement instanceof IntegerElement element) {
                    rowList.add(String.valueOf(getGeneratedIntegerValue(element.getUnitValue(),
                                                element.getStartValue(), element.isIncrease(), i)));
                }
                else if (currentElement instanceof FloatElement element) {
                    rowList.add(String.valueOf(getGeneratedFloatValue(element.getUnitValue(),
                                                element.getStartValue(), element.isIncrease(), i)));
                }
                else if (currentElement instanceof IdElement element) {
                    rowList.add(String.valueOf(getGeneratedIdValue(
                            element.getStartValue(), element.isIncrease(), i)));
                }
                else if (currentElement instanceof TimeStampElement element)
                {
                    Date dateToAdd = getGeneratedTimeStampValue(element.getDateTime(), element.getTimeUnit(),
                                                                element.getUnitValue(), element.isIncrease(), i);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    String dateString = sdf.format(dateToAdd);
                    rowList.add(dateString);
                }
                else if (currentElement instanceof NormalDistributionElement element)
                {
                    float value = getGeneratedNormalDistributionValue(random, element.getMuValue(),
                            element.getSigmaValue());
                    rowList.add(String.valueOf(value));
                }
                else if (currentElement instanceof BinomialDistributionElement element)
                {
                    int value = getGeneratedBinomialDistributionValue(random, element.getNTrialsValue(),
                                                                      element.getPValue());
                    rowList.add(String.valueOf(value));
                }
                else if (currentElement instanceof CauchyDistributionElement element)
                {
                    float value = getGeneratedCauchyDistributionValue(random, element.getX0Value(),
                                                                      element.getLambdaValue());
                    rowList.add(String.valueOf(value));
                }
                else if (currentElement instanceof RandomWalkNormalDistributionElement element)
                {
                    float value;
                    if(i == 0) {
                        value = element.getX0Value();
                        element.setPreviousValue(value);
                    }
                    else
                    {
                        float getPreviousValue = element.getPreviousValue();
                        float generateValue = getGeneratedNormalDistributionValue(random, element.getMuValue(),
                                                                                  element.getSigmaValue());
                        value = getPreviousValue + generateValue;
                        element.setPreviousValue(value);
                    }
                    rowList.add(String.valueOf(value));
                }
                else if (currentElement instanceof RandomWalkBinomialDistributionElement element)
                {
                    float value;
                    if(i == 0) {
                        value = element.getX0Value();
                        element.setPreviousValue(value);
                    }
                    else
                    {
                        float getPreviousValue = element.getPreviousValue();
                        int generateValue = getGeneratedBinomialDistributionValue(random, element.getNTrialsValue(),
                                                                                  element.getPValue());
                        value = getPreviousValue + generateValue;
                        element.setPreviousValue(value);
                    }
                    rowList.add(String.valueOf(value));
                }
                else if (currentElement instanceof RandomWalkCauchyDistributionElement element)
                {
                    float value;
                    if(i == 0) {
                        value = element.getX0Value();
                        element.setPreviousValue(value);
                    }
                    else
                    {
                        float getPreviousValue = element.getPreviousValue();
                        float generateValue = getGeneratedCauchyDistributionValue(random,
                                element.getX0Value(), element.getLambdaValue());
                        value = getPreviousValue + generateValue;
                        element.setPreviousValue(value);
                    }
                    rowList.add(String.valueOf(value));
                }
                else if(currentElement instanceof HawkessProcessElement element)
                {
                    double value;
                    if(i == 0)
                    {
                        value = element.getLambdaValue();
                        element.addPreviousValue(value);
                    }
                    else
                    {
                        List<Double> previousValues = element.getPreviousValues();
                        double[] previousDoubleValues = new double[previousValues.size()];
                        int index = 0;
                        for(Double currentValue : previousValues)
                        {
                            previousDoubleValues[index] = currentValue.floatValue();
                            index++;
                        }
                        value = getGeneratedHawkessProcessValue(previousDoubleValues, element.getLambdaValue(),
                                                                element.getAlphaValue(), element.getBetaValue());
                        element.addPreviousValue(value);
                    }
                    rowList.add(String.valueOf(value));
                }
                else if(currentElement instanceof MathElement element)
                {
                    String generatedString = getGeneratedDynamicString(element.getFormulaValue(), rowList, numberOfColumns);
                    String value = String.valueOf(eval(generatedString));
                    rowList.add(value);
                }
            }
            listOfData.add(rowList.toArray(new String[0]));
        }
        return listOfData;
    }

    /**
     *
     * Generate the formula dynamically
     *
     * @param formulaValue - raw input formula for the input text box
     * @param elementsInRow - all columns of the dataset
     * @param numberOfColumns - number of columns in dataset
     * @return - dynamically create formula
     */
    public String getGeneratedDynamicString(String formulaValue, List<String> elementsInRow, int numberOfColumns)
    {
        String newString = formulaValue;
        for(int i = 0; i < numberOfColumns - 1; i++)
        {
            String colId = "c" + String.valueOf(i+1);
            if(formulaValue.contains(colId))
            {
                newString = newString.replace(colId, elementsInRow.get(i));
            }
        }
        return newString;
    }

    /**
     *
     * Method which generates value for hawkess process
     *
     * idea from: https://javastuffnotes.blogspot.com/2016/05/hawkes-process-parameters-estimation-in.html
     *
     * @param previousValues - array of previously generated values
     * @param lambdaValue - lambda value
     * @param alphaValue - alpha value
     * @param betaValue - beta value
     * @return - the newly generated value for hawkess process
     */
    public double getGeneratedHawkessProcessValue(double[] previousValues,double lambdaValue, double alphaValue, double betaValue)
    {
        double sum = 0.0;
        int n = previousValues.length;
        for (int i = 0; i < n - 1; i++) {
            sum += Math.exp(-betaValue * (previousValues[n - 1] - previousValues[i]));
        }
        double intensity = lambdaValue + alphaValue * sum;
        return Math.max(0.0, intensity);
    }

    /**
     *
     * Method which generates value for cauchy distribution
     *
     * @param random - randomizer object
     * @param x0Value - x0 value
     * @param lambdaValue - lambda value
     * @return - the newly generated value for cauchy distribution
     */
    public float getGeneratedCauchyDistributionValue(Random random, float x0Value, float lambdaValue)
    {
        return (float) ((Math.tan(random.nextFloat() * Math.PI) * lambdaValue) + x0Value);
    }

    /**
     *
     * Method which generates value for binomial distribution
     *
     * @param random - randomizer object
     * @param nTrialsValue - number of n-trials
     * @param pValue - p value
     * @return - the newly generated value for binomial distribution
     */
    public int getGeneratedBinomialDistributionValue(Random random, int nTrialsValue, float pValue)
    {
        int found = 0;
        for(int i = 0; i < nTrialsValue; i++)
        {
            float generatedValue = random.nextFloat();
            if(generatedValue < pValue)
                found += 1;
        }
        return found;
    }

    /**
     *
     * Method which generates value for normal distribution
     *
     * @param randomValue - randomizer object
     * @param muValue - mu value
     * @param sigmaValue - sigma value
     * @return - the newly generated value for normal distribution
     */
    public float getGeneratedNormalDistributionValue(Random randomValue, float muValue, float sigmaValue)
    {
        return (float) (muValue + (randomValue.nextGaussian() * sigmaValue));
    }

    /**
     *
     * Method which generate new timestamp value
     *
     * @param date - staring date
     * @param timeUnit - unit to process
     * @param unitValue - value for unit
     * @param increase - increase or decrease value
     * @param counter - current timestamp element counter
     * @return - the newly generated timestamp value
     */
    public Date getGeneratedTimeStampValue(Date date, String timeUnit, int unitValue, boolean increase, int counter)
    {
        int amount = counter * unitValue;
        if(!increase)
            amount = amount * -1;
        // Add new value for Year
        if(timeUnit.equals(Util.timeUnitValues.get(0)))
            date = DateUtils.addYears(date, amount);
        // Add new value for Month
        else if(timeUnit.equals(Util.timeUnitValues.get(1)))
            date = DateUtils.addMonths(date, amount);
        // Add new value for Day
        else if(timeUnit.equals(Util.timeUnitValues.get(2)))
            date = DateUtils.addDays(date, amount);
        // Add new value for Hour
        else if(timeUnit.equals(Util.timeUnitValues.get(3)))
            date = DateUtils.addHours(date, amount);
        // Add new value for Minute
        else if(timeUnit.equals(Util.timeUnitValues.get(4)))
            date = DateUtils.addMinutes(date, amount);
        // Add new value for Second
        else if(timeUnit.equals(Util.timeUnitValues.get(5)))
            date = DateUtils.addSeconds(date, amount);
        // Add new value for Milisecond
        else if(timeUnit.equals(Util.timeUnitValues.get(6)))
            date = DateUtils.addMilliseconds(date, amount);
        return date;
    }

    /**
     *
     * Method which generate new id value
     *
     * @param startValue - starting id number
     * @param increase - increase or decrease value
     * @param counter - current id element counter
     * @return - the newly generated id value
     */
    public int getGeneratedIdValue(int startValue, boolean increase, int counter)
    {
        int value;
        if(increase)
            value = startValue + counter;
        else
            value = startValue - counter;
        return value;
    }

    /**
     *
     * Method which generate new float value
     *
     * @param unitValue - value for unit
     * @param startValue - starting float number
     * @param increase - increase or decrease value
     * @param counter - current float element counter
     * @return - the newly generated float
     */
    public float getGeneratedFloatValue(float unitValue, float startValue, boolean increase, int counter)
    {
        float value;
        if(increase)
            value = startValue + (float) counter * unitValue;
        else
            value = startValue - (float) counter * unitValue;
        return value;
    }

    /**
     *
     * Method which generate new integer value
     *
     * @param unitValue - value for unit
     * @param startValue - starting integer number
     * @param increase - increase or decrease value
     * @param counter - current integer element counter
     * @return - the newly generated integer
     */
    public int getGeneratedIntegerValue(int unitValue, int startValue, boolean increase, int counter)
    {
        int value;
        if(increase)
            value = startValue + counter * unitValue;
        else
            value = startValue - counter * unitValue;
        return value;
    }

    /**
     *
     * Method which create date object form 2 passed arguments
     *
     * @param timeStringValues - string which contains informations about hour, minute, second, miliseconds
     * @param dateValue - string which contains infromations about year, month, day
     * @return - newly create date object with the given date and time
     */
    public Date getAdjustedDate(String timeStringValues, String dateValue)
    {
        String[] timeArray = timeStringValues.split("\\:");
        String hourString = timeArray[0];
        String minuteString = timeArray[1];
        String[] secondsStrings = timeArray[2].split("\\.");
        String secondString = secondsStrings[0];
        String milisecondString = secondsStrings[1];

        long hour = Integer.parseInt(hourString);
        long minute = Integer.parseInt(minuteString);
        long second = Integer.parseInt(secondString);
        long milisecond = Integer.parseInt(milisecondString);

        Date newDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        try {
            Date date = sdf.parse(dateValue);
            newDate.setTime(0);
            newDate.setYear(date.getYear());
            newDate.setMonth(date.getMonth());
            newDate.setDate(date.getDate());
            newDate.setHours((int) hour);
            newDate.setMinutes((int) minute);
            newDate.setSeconds((int) second);
            newDate.setTime(newDate.getTime() + milisecond);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return newDate;
    }


    /**
     *
     * Method which creates elements based of element type
     *
     * @param rows - all row elements
     * @return - list of elements of a row
     */
    public ArrayList<Element> getRowsElements(JSONArray rows)
    {
        ArrayList<Element> elements = new ArrayList<Element>();
        for(int i = 0; i < rows.length() ; i++)
        {
            JSONObject element = (JSONObject) rows.get(i);
            String type = String.valueOf(element.get("type"));

            if(type.equals(Util.typeOfElements.get(1)))
            {
                IntegerElement integerElement = createNewIntegerElement(element);
                elements.add(integerElement);
            }
            else if(type.equals(Util.typeOfElements.get(2)))
            {
                FloatElement floatElement = createNewFloatElemet(element);
                elements.add(floatElement);
            }
            else if(type.equals(Util.typeOfElements.get(0)))
            {
                IdElement idElement = createNewIdElement(element);
                elements.add(idElement);
            }
            else if(type.equals(Util.typeOfElements.get(3)))
            {
                TimeStampElement timeStampElement = createNewTimeStampElement(element);
                elements.add(timeStampElement);
            }
            else if(type.equals(Util.typeOfElements.get(4)))
            {
                NormalDistributionElement normalDistributionElement = createNormalDistributionElement(element);
                elements.add(normalDistributionElement);
            }
            else if(type.equals(Util.typeOfElements.get(5)))
            {
                BinomialDistributionElement binomialDistributionElement = createBinomialDistributionElement(element);
                elements.add(binomialDistributionElement);
            }
            else if(type.equals(Util.typeOfElements.get(6)))
            {
                CauchyDistributionElement cauchyDistributionElement = createCauchyDistributionElement(element);
                elements.add(cauchyDistributionElement);
            }
            else if(type.equals(Util.typeOfElements.get(8)))
            {
                HawkessProcessElement hawkessProcessElement = createHawkessProcessElement(element);
                elements.add(hawkessProcessElement);
            }
            else if(type.equals(Util.typeOfElements.get(9)))
            {
                MathElement mathElement = createMathElement(element);
                elements.add(mathElement);
            }
            else if(type.equals(Util.typeOfElements.get(10)))
            {
                RandomWalkNormalDistributionElement randomWalkNormalDistributionElement =
                                                                    createRandomWalkNormalDistributionElement(element);
                elements.add(randomWalkNormalDistributionElement);
            }
            else if(type.equals(Util.typeOfElements.get(11)))
            {
                RandomWalkBinomialDistributionElement randomWalkBinomialDistributionElement =
                                                                createRandomWalkBinomialDistributionElement(element);
                elements.add(randomWalkBinomialDistributionElement);
            }
            else if(type.equals(Util.typeOfElements.get(12)))
            {
                RandomWalkCauchyDistributionElement randomWalkCauchyDistributionElement =
                                                                createRandomWalkCauchyDistributionElement(element);
                elements.add(randomWalkCauchyDistributionElement);
            }
        }
        return  elements;
    }

    /**
     *
     * Get the body which contains informations about elements from the request
     *
     * @param requestBody - body of the request
     * @return - json object containing the informations about element to process
     */
    public JSONObject getObject(String requestBody)
    {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(requestBody);
        } catch (JSONException err) {
            System.out.println("Exception : "+err.toString());
        }
        return jsonObject;
    }

    /**
     *
     * Method which return number of rows out of the request
     *
     * @param object - request object
     * @return - nuber of rows out of request
     */
    public int getNumberOfRows(JSONObject object)
    {
        String numberOfRowsString = (String) object.get("rowNumbers");

        return Integer.parseInt(numberOfRowsString);
    }

    /**
     *
     * Method which create new instance of Integer element
     *
     * @param object - contains information for creating element
     * @return - new instance of Integer element
     */
    public IntegerElement createNewIntegerElement(JSONObject object)
    {
        return IntegerElement.builder()
                .name(String.valueOf(object.get("name")))
                .type(String.valueOf(object.get("type")))
                .id(object.getInt("id"))
                .startValue(object.getInt("startValue"))
                .unitValue(object.getInt("unitValue"))
                .increase(object.getBoolean("increase"))
                .build();
    }

    /**
     *
     * Method which create new instance of Float element
     *
     * @param object - contains information for creating element
     * @return - new instance of Float element
     */
    public FloatElement createNewFloatElemet(JSONObject object)
    {
        return FloatElement.builder()
                .name(String.valueOf(object.get("name")))
                .type(String.valueOf(object.get("type")))
                .id(object.getInt("id"))
                .startValue(object.getFloat("startValue"))
                .unitValue(object.getFloat("unitValue"))
                .increase(object.getBoolean("increase"))
                .build();
    }

    /**
     *
     * Method which create new instance of Id element
     *
     * @param object - contains information for creating element
     * @return - new instance of Id element
     */
    public IdElement createNewIdElement(JSONObject object)
    {
        return IdElement.builder()
                .name(String.valueOf(object.get("name")))
                .type(String.valueOf(object.get("type")))
                .id(object.getInt("id"))
                .startValue(object.getInt("startValue"))
                .increase(object.getBoolean("increase"))
                .build();
    }

    /**
     *
     * Method which create new instance of Time Stamp element
     *
     * @param object - contains information for creating element
     * @return - new instance of Time Stamp  element
     */
    public TimeStampElement createNewTimeStampElement(JSONObject object)
    {
        Date date = null;
        String timeValues = object.getString("timeValue");
        String dateValues = object.getString("dateValue");
        date = getAdjustedDate(timeValues, dateValues);

        return TimeStampElement.builder()
                .name(String.valueOf(object.get("name")))
                .type(String.valueOf(object.get("type")))
                .id(object.getInt("id"))
                .increase(object.getBoolean("increase"))
                .dateTime(date)
                .unitValue(object.getInt("unitValue"))
                .timeUnit(String.valueOf(object.get("timeUnit")))
                .build();
    }

    /**
     *
     * Method which create new instance of Normal Distribution element
     *
     * @param object - contains information for creating element
     * @return - new instance of Normal Distribution  element
     */
    public NormalDistributionElement createNormalDistributionElement(JSONObject object)
    {
        return NormalDistributionElement.builder()
                .name(String.valueOf(object.get("name")))
                .type(String.valueOf(object.get("type")))
                .id(object.getInt("id"))
                .increase(true)
                .muValue(object.getFloat("muValue"))
                .sigmaValue(object.getFloat("sigmaValue"))
                .build();
    }

    /**
     *
     * Method which create new instance of Binomial Distribution element
     *
     * @param object - contains information for creating element
     * @return - new instance of Binomial Distribution  element
     */
    public BinomialDistributionElement createBinomialDistributionElement(JSONObject object)
    {
        return BinomialDistributionElement.builder()
                .name(String.valueOf(object.get("name")))
                .type(String.valueOf(object.get("type")))
                .id(object.getInt("id"))
                .increase(true)
                .nTrialsValue(object.getInt("nTrialsValue"))
                .pValue(object.getFloat("pValue"))
                .build();
    }

    /**
     *
     * Method which create new instance of Cachy Distribution element
     *
     * @param object - contains information for creating element
     * @return - new instance of Cachy Distribution  element
     */
    public CauchyDistributionElement createCauchyDistributionElement(JSONObject object)
    {
        return CauchyDistributionElement.builder()
                .name(String.valueOf(object.get("name")))
                .type(String.valueOf(object.get("type")))
                .id(object.getInt("id"))
                .increase(true)
                .x0Value(object.getFloat("x0Value"))
                .lambdaValue(object.getFloat("lambdaValue"))
                .build();
    }

    /**
     *
     * Method which create new instance of Random Walk Normal Distribution element
     *
     * @param object - contains information for creating element
     * @return - new instance of Random Walk Normal Distribution element
     */
    public RandomWalkNormalDistributionElement createRandomWalkNormalDistributionElement(JSONObject object)
    {
        return RandomWalkNormalDistributionElement.builder()
                .name(String.valueOf(object.get("name")))
                .type(String.valueOf(object.get("type")))
                .id(object.getInt("id"))
                .increase(true)
                .x0Value(object.getFloat("x0Value"))
                .muValue(object.getFloat("muValue"))
                .sigmaValue(object.getFloat("sigmaValue"))
                .build();
    }

    /**
     *
     * Method which create new instance of Random Walk Binomial Distribution element
     *
     * @param object - contains information for creating element
     * @return - new instance of Random Walk Binomial Distribution element
     */
    public RandomWalkBinomialDistributionElement createRandomWalkBinomialDistributionElement(JSONObject object)
    {
        return RandomWalkBinomialDistributionElement.builder()
                .name(String.valueOf(object.get("name")))
                .type(String.valueOf(object.get("type")))
                .id(object.getInt("id"))
                .increase(true)
                .x0Value(object.getFloat("x0Value"))
                .nTrialsValue(object.getInt("nTrialsValue"))
                .pValue(object.getFloat("pValue"))
                .build();
    }

    /**
     *
     * Method which create new instance of Random Walk Cauchy Distribution element
     *
     * @param object - contains information for creating element
     * @return - new instance of Random Walk Cauchy Distribution element
     */
    public RandomWalkCauchyDistributionElement createRandomWalkCauchyDistributionElement(JSONObject object)
    {
        return RandomWalkCauchyDistributionElement.builder()
                .name(String.valueOf(object.get("name")))
                .type(String.valueOf(object.get("type")))
                .id(object.getInt("id"))
                .increase(true)
                .x0Value(object.getFloat("x0Value"))
                .lambdaValue(object.getFloat("lambdaValue"))
                .build();
    }

    /**
     *
     * Method which create new instance of Hawkess Process element
     *
     * @param object - contains information for creating element
     * @return - new instance of Hawkess Process element
     */
    public HawkessProcessElement createHawkessProcessElement(JSONObject object)
    {
        return HawkessProcessElement.builder()
                .name(String.valueOf(object.get("name")))
                .type(String.valueOf(object.get("type")))
                .id(object.getInt("id"))
                .increase(true)
                .lambdaValue(object.getDouble("lambdaValue"))
                .alphaValue(object.getDouble("alphaValue"))
                .betaValue(object.getDouble("betaValue"))
                .build();
    }

    /**
     *
     * Method which create new instance of Math element
     *
     * @param object - contains information for creating element
     * @return - new instance of Math element
     */
    public MathElement createMathElement(JSONObject object)
    {
        return MathElement.builder()
                .name(String.valueOf(object.get("name")))
                .type(String.valueOf(object.get("type")))
                .id(object.getInt("id"))
                .increase(true)
                .formulaValue(String.valueOf(object.get("formulaValue")))
                .build();
    }

    /**
     *
     * Method which evaluates and executed the dynamically created formula
     *
     * Idea from: https://stackoverflow.com/questions/3422673/how-to-evaluate-a-math-expression-given-in-string-form/26227947#26227947
     *
     * @param str - string to be executed
     * @return - value of the executed string
     */
    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)` | number
            //        | functionName `(` expression `)` | functionName factor
            //        | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return +parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    if (!eat(')')) throw new RuntimeException("Missing ')'");
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    if (eat('(')) {
                        x = parseExpression();
                        if (!eat(')')) throw new RuntimeException("Missing ')' after argument to " + func);
                    } else {
                        x = parseFactor();
                    }
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }
}
