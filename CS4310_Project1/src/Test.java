import java.io.FileNotFoundException;
import java.util.Scanner;

public class Test {
	public static void main(String[] args) throws FileNotFoundException {
		
		run();
		
		
	}
	public static void run() throws FileNotFoundException {
		int choice =-1;
		boolean TRUE = true;
		while(TRUE) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Which one you want to run\n1.FCFS\n2.SJF\n3.Priority_Sceduling\n4.RR\n5.QUIT");
		choice = sc.nextInt();
		String file="";
		Scanner sc2 = new Scanner(System.in);
		System.out.println("Enter file path");
		file = sc2.next();
		
		switch (choice) {
			case 1:
				FCFS t1 = new FCFS();
				t1.run();
				break;
			case 2:
				SJF t2 = new SJF();
				t2.run();
				break;
			case 3:
				Preemptive_Priority_Scheduling t3 = new Preemptive_Priority_Scheduling();
				t3.run();
				break;
			case 4:
				RR t4 = new RR();
				t4.run();
				break;
			case 5:
				TRUE =false;
				break;
			default:
				System.out.print("<!--\n" + 
						"\nBad Input" + 
						"                       ::\n" + 
						"\n" + 
						"                      :;J7, :,                        ::;7:\n" + 
						"\n" + 
						"                      ,ivYi, ,                       ;LLLFS:\n" + 
						"\n" + 
						"                      :iv7Yi                       :7ri;j5PL\n" + 
						"\n" + 
						"                     ,:ivYLvr                    ,ivrrirrY2X,\n" + 
						"\n" + 
						"                     :;r@Wwz.7r:                :ivu@kexianli.\n" + 
						"\n" + 
						"                    :iL7::,:::iiirii:ii;::::,,irvF7rvvLujL7ur\n" + 
						"\n" + 
						"                   ri::,:,::i:iiiiiii:i:irrv177JX7rYXqZEkvv17\n" + 
						"\n" + 
						"                ;i:, , ::::iirrririi:i:::iiir2XXvii;L8OGJr71i\n" + 
						"\n" + 
						"              :,, ,,:   ,::ir@mingyi.irii:i:::j1jri7ZBOS7ivv,\n" + 
						"\n" + 
						"                 ,::,    ::rv77iiiriii:iii:i::,rvLq@huhao.Li\n" + 
						"\n" + 
						"             ,,      ,, ,:ir7ir::,:::i;ir:::i:i::rSGGYri712:\n" + 
						"\n" + 
						"           :::  ,v7r:: ::rrv77:, ,, ,:i7rrii:::::, ir7ri7Lri\n" + 
						"\n" + 
						"          ,     2OBBOi,iiir;r::        ,irriiii::,, ,iv7Luur:\n" + 
						"\n" + 
						"        ,,     i78MBBi,:,:::,:,  :7FSL: ,iriii:::i::,,:rLqXv::\n" + 
						"\n" + 
						"        :      iuMMP: :,:::,:ii;2GY7OBB0viiii:i:iii:i:::iJqL;::\n" + 
						"\n" + 
						"       ,     ::::i   ,,,,, ::LuBBu BBBBBErii:i:i:i:i:i:i:r77ii\n" + 
						"\n" + 
						"      ,       :       , ,,:::rruBZ1MBBqi, :,,,:::,::::::iiriri:\n" + 
						"\n" + 
						"     ,               ,,,,::::i:  @arqiao.       ,:,, ,:::ii;i7:\n" + 
						"\n" + 
						"    :,       rjujLYLi   ,,:::::,:::::::::,,   ,:i,:,,,,,::i:iii\n" + 
						"\n" + 
						"    ::      BBBBBBBBB0,    ,,::: , ,:::::: ,      ,,,, ,,:::::::\n" + 
						"\n" + 
						"    i,  ,  ,8BMMBBBBBBi     ,,:,,     ,,, , ,   , , , :,::ii::i::\n" + 
						"\n" + 
						"    :      iZMOMOMBBM2::::::::::,,,,     ,,,,,,:,,,::::i:irr:i:::,\n" + 
						"\n" + 
						"    i   ,,:;u0MBMOG1L:::i::::::  ,,,::,   ,,, ::::::i:i:iirii:i:i:\n" + 
						"\n" + 
						"    :    ,iuUuuXUkFu7i:iii:i:::, :,:,: ::::::::i:i:::::iirr7iiri::\n" + 
						"\n" + 
						"    :     :rk@Yizero.i:::::, ,:ii:::::::i:::::i::,::::iirrriiiri::,\n" + 
						"\n" + 
						"     :      5BMBBBBBBSr:,::rv2kuii:::iii::,:i:,, , ,,:,:i@petermu.,\n" + 
						"\n" + 
						"          , :r50EZ8MBBBBGOBBBZP7::::i::,:::::,: :,:,::i;rrririiii::\n" + 
						"\n" + 
						"              :jujYY7LS0ujJL7r::,::i::,::::::::::::::iirirrrrrrr:ii:\n" + 
						"\n" + 
						"           ,:  :@kevensun.:,:,,,::::i:i:::::,,::::::iir;ii;7v77;ii;i,\n" + 
						"\n" + 
						"           ,,,     ,,:,::::::i:iiiii:i::::,, ::::iiiir@xingjief.r;7:i,\n" + 
						"\n" + 
						"        , , ,,,:,,::::::::iiiiiiiiii:,:,:::::::::iiir;ri7vL77rrirri::\n" + 
						"\n" + 
						"         :,, , ::::::::i:::i:::i:i::,,,,,:,::i:i:::iir;@Secbone.ii:::\n" + 
						"\n" + 
						" \n" + 
						"\n" + 
						"-->Bad Input\n" + 
						"\n" + 
						"");
			
				
		}
		}
				
	}

}

