package project1;
import java.util.*;
import java.sql.*;
public class atm {
  public static void main(String[] args)throws SQLException
	{
		Scanner sc = new Scanner(System.in);
		int a;
		System.out.println("1.Load Cash to ATM");
		System.out.println("2.Show Customer Details");
		System.out.println("3.Show ATM Operations");
		a=sc.nextInt();
		try
		{
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm","root","root");
		switch (a)
		{
		case 1:
			PreparedStatement s = c.prepareStatement("insert into atm values(?,?,?)");
			int d=sc.nextInt();
			int v=sc.nextInt();
			int x=d*v;
			s.setInt(1, d);
			s.setInt(2, v);
			s.setInt(3, x);
			int i = s.executeUpdate();
			break;
		case 2:
			PreparedStatement m=c.prepareStatement("select * from customer");
			ResultSet r= m.executeQuery();
			System.out.println("Accountno AccountHolder  PinNumber  AccountBalance");
			while(r.next())
			{
				System.out.println(r.getInt(1)+"        "+r.getString(2)+"         "+r.getInt(3)+"         "+r.getInt(4));
			}
			break;
		case 3:
			System.out.println("1.Check Balance");
			System.out.println("2.Withdraw Money");
			System.out.println("3.Transfer Money");
			System.out.println("4.Check ATM Balance");
			int t=sc.nextInt();
			switch(t)
			{
			case 1:
				System.out.println("Enter AccountNo: ");
				int g=sc.nextInt();
				PreparedStatement f=c.prepareStatement("select AccountBalance from customer where Accountno=(?)");
				f.setInt(1,g);
				ResultSet r1= f.executeQuery();
				while(r1.next())
				{
					System.out.println(r1.getInt(1)+" ");
				}
				break;
			case 2:
				System.out.println("Enter your Accountno: ");
				int Acc_no=sc.nextInt();
				System.out.println("Enter Amount to withdraw: ");
				int withdrawamt=sc.nextInt();
				if(withdrawamt%100==0&&withdrawamt<=10000&&withdrawamt>=100)
				{
					PreparedStatement io=c.prepareStatement("update customer set AccountBalance=AccountBalance-"+withdrawamt+" where Accountno="+Acc_no);
					io.executeUpdate();
					int temp_amt=withdrawamt;
					int two_thous=0;
					int five_hun=0;
					int one_hun=0;
					if(temp_amt%2000==0) {
						two_thous%=2000;
						temp_amt/=2000;
						System.out.println("temp amt: "+temp_amt);
						PreparedStatement two_p=c.prepareStatement("select Number from atm where Denomination=2000");
						
						ResultSet two_t= two_p.executeQuery();
						
						int atm_amt = two_t.getInt(2);
						System.out.println("atm amt: "+atm_amt);
						atm_amt-=temp_amt;
//						System.out.println(temp_amt);
//						PreparedStatement io=c.prepareStatement("update atm set Number="+two_thous);
						PreparedStatement update_in_atm = c.prepareStatement(" update atm set Number=? , Value=? where Denomination = 2000;");

						int up_amt=2000*two_thous;
//						update_in_atm.setInt(1, 2000);
						update_in_atm.setInt(1, atm_amt);
						update_in_atm.setInt(2, up_amt);
						update_in_atm.executeUpdate();
					}
					if(temp_amt%500==0&&temp_amt%2000!=0) {
						five_hun%=500;
						temp_amt/=500;
//						PreparedStatement io=c.prepareStatement("update atm set Number="+five_hun);
						PreparedStatement update_in_atm = c.prepareStatement("update atm set Number=? , Value=? where Denomination = 500;");

						int up_amt=500*five_hun;
//						update_in_atm.setInt(1, 500);
						update_in_atm.setInt(1, five_hun);
						update_in_atm.setInt(2, up_amt);
						update_in_atm.executeUpdate();
					}
					
					if(temp_amt%100==0&&temp_amt%500!=0) {
						one_hun%=100;
						temp_amt/=100;
//						PreparedStatement io=c.prepareStatement("update atm set Number="+one_hun);
						PreparedStatement update_in_atm = c.prepareStatement("update atm set Number=? , Value=? where Denomination = 100;");

						int up_amt=100*one_hun;
//						update_in_atm.setInt(1, 100);
						update_in_atm.setInt(1, one_hun);
						update_in_atm.setInt(2, up_amt);
						update_in_atm.executeUpdate();
					}
					
				}
				else
				{
					System.out.println("Withdraw failed");
				}
				break;
			case 3:
				System.out.println("Enter your AccountNo: ");
				int acn=sc.nextInt();
				System.out.println("Enter the Accountno of Reciever: ");
				int accn=sc.nextInt();
				System.out.println("Enter the Amount: ");
				int amts=sc.nextInt();
				PreparedStatement o=c.prepareStatement("select AccountBalance from customer where Accountno=(?)");
				o.setInt(1,acn);
				ResultSet r3= o.executeQuery();
				while(r3.next())
				{
					System.out.println(r3.getInt(1)-amts+" ");
				}
				PreparedStatement pk=c.prepareStatement("update customer set AccountBalance=AccountBalance-"+amts+" where Accountno="+acn);
				pk.executeUpdate();
				PreparedStatement ok=c.prepareStatement("select AccountBalance from customer where Accountno=(?)");
				ok.setInt(1,accn);
				ResultSet r4= ok.executeQuery();
				while(r4.next())
				{
					System.out.println(r4.getInt(1)+amts+" ");
				}
				PreparedStatement ik=c.prepareStatement("update customer set AccountBalance=AccountBalance+"+amts+" where Accountno="+accn);
				ik.executeUpdate();
				System.out.println("Amount transfered successfully!");
				break;
			case 4:
				System.out.println("ATM Balance is: ");
				PreparedStatement p=c.prepareStatement("select * from atm");
				ResultSet r2= p.executeQuery();
				while(r2.next())
				{
					System.out.println(r2.getInt(1)+" "+r2.getInt(2)+" "+r2.getInt(3));
				}
			   break;
			}
			break;
		}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	}

}
