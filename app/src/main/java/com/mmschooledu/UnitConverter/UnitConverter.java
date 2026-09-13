package com.mmschooledu.UnitConverter;


import java.util.*;

public class UnitConverter
{
	private Map<String,Double>LengthMap;
	private Map<String,Double>AreaMap;
	private Map<String,Double>VolumeMap;
	private Map<String,Double>MassMap;

	public static final String MODE_LENGTH="အလျား";
	public static final String MODE_AREA="ဧရိယာ";
	public static final String MODE_VOLUME="ထုထည်";
	public static final String MODE_MASS="အလေးချိန်";

	public UnitConverter(){
		LengthMap=new HashMap<String,Double>();
		AreaMap=new HashMap<String,Double>();
		VolumeMap=new HashMap<String,Double>();
		MassMap=new HashMap<String,Double>();

		LengthMap.put("နာနိုမီတာ",1.0e-9);
		LengthMap.put("မိုက်ခရိုမီတာ",1.0e-6);
		LengthMap.put("မီလီမီတာ",0.001);
		LengthMap.put("စင်တီမီတာ",0.01);
		LengthMap.put("ဒက်စီမီတာ",0.1);
		LengthMap.put("မီတာ",1.0);
		LengthMap.put("ဟက်တိုမီတာ",100.0);
		LengthMap.put("ကီလိုမီတာ",1000.0);
		LengthMap.put("လက်မ",0.0254);
		LengthMap.put("ပေ",0.3048);
		LengthMap.put("ကိုက်",0.9144);
		LengthMap.put("မိုင်",1609.344);

		LengthMap.put("ဆံခြည်",7.9375e-5);
		LengthMap.put("နှမ်း",7.9375e-4);
		LengthMap.put("မုယော",4.7625e-4);
		LengthMap.put("လက်သစ်",1.905e-2);
		LengthMap.put("မိုက်",0.1524);
		LengthMap.put("ထွာ",0.2286);
		LengthMap.put("တောင်",0.4572);
		LengthMap.put("လံ",1.8288);
		LengthMap.put("တာ",3.2004);
		LengthMap.put("ဥဿဘ",64.008);
		LengthMap.put("ကောသ",1280.16);
		LengthMap.put("ဂါ၀ုတ်",5120.64);
		LengthMap.put("ယူဇနာ",20482.56);

		AreaMap.put("square_nanometer",1.0e-18);
		AreaMap.put("square_micrometer",1.0e-12);
		AreaMap.put("square_millimeter",1.0e-6);
		AreaMap.put("square_centimeter",1.0e-4);
		AreaMap.put("square_decimeter",0.01);
		AreaMap.put("square_meter",1.0);
		AreaMap.put("square_kilometer",1.0e6);
		AreaMap.put("ဟက်တာ",1.0e5);
		AreaMap.put("ဧက",4046.8564224);
		AreaMap.put("square_inch",0.00064516);
		AreaMap.put("square_foot",0.09290304);
		AreaMap.put("square_mile",2589988.11);

		VolumeMap.put("milliliter",1.0e-6);
		VolumeMap.put("liter",1.0e-3);
		VolumeMap.put("cubic_millimeter",1.0e-9);
		VolumeMap.put("cubic_centimeter",1.0e-6);
		VolumeMap.put("cubic_decimeter",0.001);
		VolumeMap.put("gallon",0.0037854118);
		VolumeMap.put("cubic_kilometer",1.0e9);
		VolumeMap.put("cubic_inch",0.0000163871);
		VolumeMap.put("cubic_foot",0.0283168466);

		VolumeMap.put("လမြူ",7.99118e-5);
		VolumeMap.put("လမြက်",1.59824e-4);
		VolumeMap.put("လမယ်",3.19647e-4);
		VolumeMap.put("စလယ်",6.39294e-4);
		VolumeMap.put("ခွက်",1.27859e-3);
		VolumeMap.put("ပြည်",2.55718e-3);
		VolumeMap.put("စိတ်",1.02287e-2);
		VolumeMap.put("ခွဲ",2.04574e-2);
		VolumeMap.put("တင်း",4.09148e-2);

		MassMap.put("တန်",1.0e3);
		MassMap.put("ကီလိုဂရမ်",1.0);
		MassMap.put("ဂရမ်",1.0e-3);
		MassMap.put("မီလီဂရမ်",1.0e-6);
		MassMap.put("မိုက်ခရိုဂရမ်",1.0e-9);
		MassMap.put("အောင်စ",0.028);
		MassMap.put("ပေါင်",0.45359237);

		MassMap.put("ရွေးလေး",1.36078e-4);
		MassMap.put("ရွေးကြီး",2.72155e-4);
		MassMap.put("ပဲသား",1.02058e-3);
		MassMap.put("မူးသား",2.04117e-3);
		MassMap.put("မတ်သား",4.08233e-3);
		MassMap.put("ငါးမူးသား",8.16466e-3);
		MassMap.put("ကျပ်သား",0.0163293);
		MassMap.put("အ၀က်သား",0.204117);
		MassMap.put("အစိတ်သား",0.408233);
		MassMap.put("ငါးဆယ်သား",0.816466);
		MassMap.put("ပိဿာ",1.63293);
		MassMap.put("အချိန်တစ်ရာ",163.293);

	}

	public double Convert(String mode,double val,String from,String to){
		double fromval=0, toval=1;
		switch(mode){
			case MODE_LENGTH:
				fromval=LengthMap.get(from);
				toval=LengthMap.get(to);
				break;
			case MODE_AREA:
				fromval=AreaMap.get(from);
				toval=AreaMap.get(to);
				break;
			case MODE_VOLUME:
				fromval=VolumeMap.get(from);
				toval=VolumeMap.get(to);
				break;
			case MODE_MASS:
				fromval=MassMap.get(from);
				toval=MassMap.get(to);
				break;
		}
		try{
			double converted=val*fromval/toval;
			return converted;
		}catch(Exception e){
			return 0;
		}
	}

}
