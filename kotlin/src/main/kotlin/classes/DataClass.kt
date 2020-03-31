package classes

//data class means auto generating equals(), hashCode(), toString(), copy(), componentN()
data class DataClass(val name: String, val amount: Int)

//��������� ����������� ������ ����� ��� ������� ���� ��������;
//��� ��������� ���������� ������������ ������ ���� ��������, ��� val ��� var;
//������ ������ �� ����� ���� ������������, open, sealed ��� inner;
//����-������ �� ����� ������������� �� ������ ������� (�� ����� ������������� ����������).