---
title: 'TDD in Python'
weight: 3
author: Arne Duyver
draft: false
---

## Unit tests

Je zou simpelweg de ingebouwde `assert` functie in Python kunnen gebruiken om testen te schrijven zoals hieronder is weergegeven. Je kan de testfile dan runnen met `python3 calculator_test.py` en wanneer een assert niet klopt zal je een `AssertionError` krijgen. 

- `calculator.py`:
```python
def divide(teller, noemer):
    if (noemer == 0):
        raise ZeroDivisionError
    return teller/noemer
```

- `calculator_test.py`:
```python
from calculator import divide

def gegevenTeller2Noemer1_wanneerDivide_danResult2():
    assert divide(2,1) == 1

if __name__ == "__main__":
    gegevenTeller2Noemer1_wanneerDivide_danResult2()
``` 

Dit geeft echter niet zo een mooie output zoals we in Java met Junit hebben gezien en Python komt standaard met een testmodule genaamd `unittest`. We kunnen onze testfile dan uitvoeren met `python3 -m unittest calculator_test.py`. Unittest beschouwd onze functie echter nog niet als een test, hiervoor moeten we een aantal dingen wijzigen aan onze testfile:
1. Een testklasse aanmaken genaamd die erft van de klasse unittest.TestCase: `TestCalculator(unittest.TestCase)`
2. De testen als klasse methodes definiëren
3. De methoden moeten beginnen met `test_`
4. Je moet asserten met `self.assert...` (self verwijst hierbij naar de klasse zelf)
5. De main methode van unittest oproepen: `unittest.main()`

```python
import unittest
from calculator import divide

class TestCalculator(unittest.TestCase):
    def test_gegevenTeller2Noemer1_wanneerDivide_danResult2(self):
        result = divide(2, 1)
        self.assertEqual(result, 1)  # Let op: 2/1 = 2, dus deze test zal falen!

if __name__ == "__main__":
    unittest.main()
```

Nu krijgen we bij het runnen van deze testfile een mooiere output:
```bash
arne@LT3210121:~/ses/tddpythondemo/src$ python3 -m unittest calculator_test.py 
.
----------------------------------------------------------------------
Ran 1 test in 0.000s

OK
```

We kunnen onze testfile nu uitbreiden net zoals we dat in Java gedaan hebben:
```python
import unittest
from calculator import divide

class TestCalculator(unittest.TestCase):
    def test_gegevenTeller2Noemer1_wanneerDivide_danResult2(self):
        result = divide(2, 1)
        self.assertEqual(result, 2) 
    
    def test_gegevenTeller2Noemer4_wanneerDivide_danResult0point5(self):
        result = divide(2, 4)
        self.assertEqual(result, 0.5)
    
    def test_gegevenTellerXNoemer0_wanneerDivide_danZeroDivisionError(self):
        with self.assertRaises(ZeroDivisionError):
            divide(2, 0)

    def test_voorbeeld_falende_test(self):
        result = divide(2, 1)
        self.assertEqual(result, 4)
    

if __name__ == "__main__":
    unittest.main()
```

{{% notice info %}}
Er bestaan ook nog andere modules om testen uit te voeren in Python. Een zeer populaire module is **Pytest**. Deze module biedt onder andere een iets mooiere output met groene en rode highlights voor respectievelijk geslaagde en gefaalde testen.
{{% /notice %}}

## Setting up VSCode for Python testing

Je kan de built-in VSCode tool gebruiken voor debugging (gecombineerd met de juiste extensies) om een mooie Gui interface te hebben voor de testen. Hiervoor klik je op onderstaande view panel en configureer je het juiste Testing framework, de plaats waar VSCode moet zoeken naar de test files en hoe de test files noemen.

<figure style="display: flex; align-items: center; flex-direction: column;">
    <img src="/img/pythontestvscode.png" style="max-height: 40rem;"/>
    <figcaption ><strong><i>Test suite for VSCode</i></strong></figcation>
</figure>

Nadat je alles correct geconfigureerd hebt zouden je testen er als volgt moeten uitzien:

<figure style="display: flex; align-items: center; flex-direction: column;">
    <img src="/img/voorbeeldtestenpython.png" style="max-height: 40rem;"/>
    <figcaption ><strong><i>Voorbeeld testoutput Python</i></strong></figcation>
</figure>

## Integration tests

Net zoals in Java kunnen we Mocks hiervoor gebruiken om Test Doubles aan te maken. Gelukkig is 'mocken' een functionaliteit die rechtstreeks in `unittest` is ingebouwd. De `doubler.py`-file en `doubler_test.py`-file analoog aan het voorbeeld in [integration testen in Java](/content/5-tdd/tdd-java.md#integration-testen):

- `doubler.py`: We geven nu een referentie naar de functie mee als parameter om te voldoen aan het principe van Dependency Injection.
```python
from calculator import divide

def double_calculator(operation, x, y):
    result = operation(x, y)
    return result * 2
```

- `doubler_test.py`:
```python
import unittest
from unittest.mock import patch
from doubler import double_calculator

class TestCalculator(unittest.TestCase):
    @patch("doubler.divide")  # Mock de geïmporteerde `divide` functie in `doubler.py`
    def test_gegevenOperationDivideX2Y1_wanneerDoubleCalculator_danResultIs4(self, mock_divide):
        # Arrange
        # De mock werd meegegeven als parameter en je stelt nu de return value in
        mock_divide.return_value = 2.0
        
        # Act
        result = double_calculator(mock_divide, 2, 1)
        
        # Assert
        self.assertEqual(result, 4.0)
        
        # Verifieer dat `divide` werd aangeroepen met (2, 1)
        mock_divide.assert_called_once_with(2, 1)

if __name__ == "__main__":
    unittest.main()
```

## End-to-end testen

_Zie [TDD-pagina](/5-tdd/_index.md#3-end-to-end-testing-rood)_

## Opgaven: oefenen op het gebruik van een debugger

Onderstaande Python file bevat een aantal functies die getest worden met een testfile. Er zijn echter een heel deel testen die nog falen. Verbeter nu de python functies zodat alle testen slagen:
- Python file: `functions.py`
```python
def max_in_list(lst):
    max_val = lst[0]
    for num in lst:
        if num < max_val:  
            max_val = num
    return max_val

def factorial(n):
    if n == 0:
        return 1
    result = 1
    for i in range(1, n):  
        result *= i
    return result

def is_even(num):
    return num % 2 == 1  

def count_positive(numbers):
    count = 0
    for num in numbers:
        if num >= 0:  
            count += 1
    return count

def is_prime(n):
    if n <= 1:
        return False
    for i in range(2, int(n**0.5)): 
        if n % i == 0:
            return False
    return True
```

- Python test file: `functions_test.py`
```python
import unittest
from functions import max_in_list, factorial, is_even, count_positive, is_prime

class TestFunctions(unittest.TestCase):
    def test_gegeven3en5en2_wanneerMaxInList_dan5(self):
        self.assertEqual(max_in_list([3, 5, 2]), 5)
    
    def test_gegevenMin1enMin5enMin3_wanneerMaxInList_danMin1(self):
        self.assertEqual(max_in_list([-1, -5, -3]), -1)

    def test_gegeven0_wanneerFactorial_dan1(self):
        self.assertEqual(factorial(0), 1)
        
    def test_gegeven5_wanneerFactorial_dan120(self):
        self.assertEqual(factorial(5), 120)

    def test_gegeven4_wanneerIsEven_danTrue(self):
        self.assertTrue(is_even(4))
    
    def test_gegeven5_wanneerIsEven_danFalse(self):
        self.assertFalse(is_even(5))

    def test_gegevenMin1en0en5_wanneerCountPositive_dan1(self):
        self.assertEqual(count_positive([-1, 0, 5]), 1)
    
    def test_gegevenMin2enMin3_wanneerCountPositive_dan0(self):
        self.assertEqual(count_positive([-2, -3]), 0)

    def test_gegeven4en5en6_wanneerCountPositive_dan3(self):
        self.assertEqual(count_positive([4, 5, 6]), 3)

    def test_gegeven1_wanneerIsPrime_danFalse(self):
        self.assertFalse(is_prime(1))

    def test_gegeven2_wanneerIsPrime_danTrue(self):
        self.assertTrue(is_prime(2))

    def test_gegeven3_wanneerIsPrime_danTrue(self):
        self.assertTrue(is_prime(3))

    def test_gegeven4_wanneerIsPrime_danFalse(self):
        self.assertFalse(is_prime(4))

    def test_gegeven9_wanneerIsPrime_danFalse(self):
        self.assertFalse(is_prime(9))

    def test_gegeven11_wanneerIsPrime_danTrue(self):
        self.assertTrue(is_prime(11))

if __name__ == '__main__':
    unittest.main()
```

<!-- EXSOL -->
<!-- <details closed>
<summary><i><b><span style="color: #03C03C;">Solution:</span> Klik hier om de code te zien/verbergen van de verbeterde `functions.py`</b></i>🔽</summary>
<p>

```python
# functions.py
def max_in_list(lst):
    max_val = lst[0]
    for num in lst:
        if num < max_val:  # Fout: moet > zijn
            max_val = num
    return max_val

def factorial(n):
    if n == 0:
        return 1
    result = 1
    for i in range(1, n):  # Fout: moet range(1, n+1) zijn
        result *= i
    return result

def is_even(num):
    return num % 2 == 1  # Fout: moet == 0 zijn

def count_positive(numbers):
    count = 0
    for num in numbers:
        if num >= 0:  # Fout: moet > 0 zijn
            count += 1
    return count

def is_prime(n):
    if n <= 1:
        return False
    for i in range(2, int(n**0.5)):  # Fout: moet int(n**0.5) + 1 zijn
        if n % i == 0:
            return False
    return True
```

</p>
</details> -->
