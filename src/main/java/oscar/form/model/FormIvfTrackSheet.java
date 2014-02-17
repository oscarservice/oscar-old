package oscar.form.model;

import java.io.Serializable;
import javax.persistence.*;
import org.oscarehr.common.model.AbstractModel;
import java.sql.Timestamp;
import java.util.Date;

/**
 * The persistent class for the formivftracksheet database table.
 * 
 */
@Entity
@Table(name="formivftracksheet")
public class FormIvfTrackSheet extends AbstractModel<Integer> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="a_txt", length=2)
	private String aTxt;

	@Column(length=60)
	private String address;

	private byte antibiotics;

	private byte assisted;

	private byte bcs;

	private byte blastocyst;

	@Column(length=20)
	private String city;

	@Column(length=255)
	private String comment1;

	private byte consents;

	private byte crinone;

	@Column(length=16)
	private String date1;

	@Column(length=16)
	private String date10;

	@Column(length=16)
	private String date11;

	@Column(length=16)
	private String date12;

	@Column(length=16)
	private String date13;

	@Column(length=16)
	private String date14;

	@Column(length=16)
	private String date15;

	@Column(length=16)
	private String date16;

	@Column(length=16)
	private String date17;

	@Column(length=16)
	private String date18;

	@Column(length=16)
	private String pgb;
	
	@Column(length=16)
	private String date2;

	@Column(length=16)
	private String date3;

	@Column(length=16)
	private String date4;

	@Column(length=16)
	private String date5;

	@Column(length=16)
	private String date6;

	@Column(length=16)
	private String date7;

	@Column(length=16)
	private String date8;

	@Column(length=16)
	private String date9;

	@Column(name="demographic_no", nullable=false)
	private int demographicNo;

	@Column(length=20)
	private String diagnosis;

	@Column(length=16)
	private String dob;
	
	@Column(length=20)
	private String age;

	private byte donor;

	@Column(length=7)
	private String endometrial1;

	@Column(length=7)
	private String endometrial10;

	@Column(length=7)
	private String endometrial11;

	@Column(length=7)
	private String endometrial12;

	@Column(length=7)
	private String endometrial13;

	@Column(length=7)
	private String endometrial14;

	@Column(length=7)
	private String endometrial15;

	@Column(length=7)
	private String endometrial16;

	@Column(length=7)
	private String endometrial17;

	@Column(length=7)
	private String endometrial18;

	@Column(length=7)
	private String endometrial2;

	@Column(length=7)
	private String endometrial3;

	@Column(length=7)
	private String endometrial4;

	@Column(length=7)
	private String endometrial5;

	@Column(length=7)
	private String endometrial6;

	@Column(length=7)
	private String endometrial7;

	@Column(length=7)
	private String endometrial8;

	@Column(length=7)
	private String endometrial9;

	private byte endometrin;

	@Column(length=5)
	private String estradiol1;

	@Column(length=5)
	private String estradiol10;

	@Column(length=5)
	private String estradiol11;

	@Column(length=5)
	private String estradiol12;

	@Column(length=5)
	private String estradiol13;

	@Column(length=5)
	private String estradiol14;

	@Column(length=5)
	private String estradiol15;

	@Column(length=5)
	private String estradiol16;

	@Column(length=5)
	private String estradiol17;

	@Column(length=5)
	private String estradiol18;

	@Column(length=5)
	private String estradiol2;

	@Column(length=5)
	private String estradiol3;

	@Column(length=5)
	private String estradiol4;

	@Column(length=5)
	private String estradiol5;

	@Column(length=5)
	private String estradiol6;

	@Column(length=5)
	private String estradiol7;

	@Column(length=5)
	private String estradiol8;

	@Column(length=5)
	private String estradiol9;

	@Column(name="for_et", length=20)
	private String forEt;

	@Temporal(TemporalType.DATE)
	private Date formCreated;

	@Column(nullable=false)
	private Timestamp formEdited;

	private byte fresh;

	private byte frozen;

	@Column(length=6)
	private String fsh;

	@Column(name="funded_n")
	private byte fundedN;

	@Column(name="funded_y")
	private byte fundedY;

	@Column(name="g_txt", length=2)
	private String gTxt;

	private byte hcg;

	@Column(length=24)
	private String healthNum;

	private byte icsi;

	private byte ivf;

	@Column(length=6)
	private String ivfCycleNumber;

	@Column(length=5)
	private String lh1;

	@Column(length=5)
	private String lh10;

	@Column(length=5)
	private String lh11;

	@Column(length=5)
	private String lh12;

	@Column(length=5)
	private String lh13;

	@Column(length=5)
	private String lh14;

	@Column(length=5)
	private String lh15;

	@Column(length=5)
	private String lh16;

	@Column(length=5)
	private String lh17;

	@Column(length=5)
	private String lh18;

	@Column(length=5)
	private String lh2;

	@Column(length=5)
	private String lh3;

	@Column(length=5)
	private String lh4;

	@Column(length=5)
	private String lh5;

	@Column(length=5)
	private String lh6;

	@Column(length=5)
	private String lh7;

	@Column(length=5)
	private String lh8;

	@Column(length=5)
	private String lh9;

	@Column(length=20)
	private String medication;

	@Column(length=3)
	private String menopur1;

	@Column(length=3)
	private String menopur10;

	@Column(length=3)
	private String menopur11;

	@Column(length=3)
	private String menopur12;

	@Column(length=3)
	private String menopur13;

	@Column(length=3)
	private String menopur14;

	@Column(length=3)
	private String menopur15;

	@Column(length=3)
	private String menopur16;

	@Column(length=3)
	private String menopur17;

	@Column(length=3)
	private String menopur18;

	@Column(length=20)
	private String osc;
	
	@Column(length=3)
	private String menopur2;

	@Column(length=3)
	private String menopur3;

	@Column(length=3)
	private String menopur4;

	@Column(length=3)
	private String menopur5;

	@Column(length=3)
	private String menopur6;

	@Column(length=3)
	private String menopur7;

	@Column(length=3)
	private String menopur8;

	@Column(length=3)
	private String menopur9;

	@Column(length=5)
	private String orgalutran1;

	@Column(length=5)
	private String orgalutran10;

	@Column(length=5)
	private String orgalutran11;

	@Column(length=5)
	private String orgalutran12;

	@Column(length=5)
	private String orgalutran13;

	@Column(length=5)
	private String orgalutran14;

	@Column(length=5)
	private String orgalutran15;

	@Column(length=5)
	private String orgalutran16;

	@Column(length=5)
	private String orgalutran17;

	@Column(length=5)
	private String orgalutran18;

	@Column(length=5)
	private String orgalutran2;

	@Column(length=5)
	private String orgalutran3;

	@Column(length=5)
	private String orgalutran4;

	@Column(length=5)
	private String orgalutran5;

	@Column(length=5)
	private String orgalutran6;

	@Column(length=5)
	private String orgalutran7;

	@Column(length=5)
	private String orgalutran8;

	@Column(length=5)
	private String orgalutran9;

	@Column(name="p_txt", length=2)
	private String pTxt;

	private byte p4supps;

	@Column(length=16)
	private String partnerdob;

	@Column(length=30)
	private String partnerFirstName;

	@Column(length=24)
	private String partnerHealthNum;

	@Column(length=30)
	private String partnerSurname;

	@Column(length=30)
	private String patientFirstName;

	@Column(length=30)
	private String patientSurname;

	private byte pesa;

	@Column(length=18)
	private String phone;

	@Column(length=30)
	private String physician;

	@Column(length=9)
	private String postalCode;

	@Column(length=2)
	private String previousfresh;

	@Column(length=2)
	private String previousfrozen;

	@Column(name="primary_nurse", length=20)
	private String primaryNurse;

	@Column(length=5)
	private String proges1;

	@Column(length=5)
	private String proges10;

	@Column(length=5)
	private String proges11;

	@Column(length=5)
	private String proges12;

	@Column(length=5)
	private String proges13;

	@Column(length=5)
	private String proges14;

	@Column(length=5)
	private String proges15;

	@Column(length=5)
	private String proges16;

	@Column(length=5)
	private String proges17;

	@Column(length=5)
	private String proges18;

	@Column(length=5)
	private String proges2;

	@Column(length=5)
	private String proges3;

	@Column(length=5)
	private String proges4;

	@Column(length=5)
	private String proges5;

	@Column(length=5)
	private String proges6;

	@Column(length=5)
	private String proges7;

	@Column(length=5)
	private String proges8;

	@Column(length=5)
	private String proges9;

	@Column(name="provider_no")
	private int providerNo;

	@Column(length=7)
	private String puregon1;

	@Column(length=7)
	private String puregon10;

	@Column(length=7)
	private String puregon11;

	@Column(length=7)
	private String puregon12;

	@Column(length=7)
	private String puregon13;

	@Column(length=7)
	private String puregon14;

	@Column(length=7)
	private String puregon15;

	@Column(length=7)
	private String puregon16;

	@Column(length=7)
	private String puregon17;

	@Column(length=7)
	private String puregon18;

	@Column(length=20)
	private String mml;
	
	@Column(length=7)
	private String puregon2;

	@Column(length=7)
	private String puregon3;

	@Column(length=7)
	private String puregon4;

	@Column(length=7)
	private String puregon5;

	@Column(length=7)
	private String puregon6;

	@Column(length=7)
	private String puregon7;

	@Column(length=7)
	private String puregon8;

	@Column(length=7)
	private String puregon9;

	@Column(name="r_l01", length=2)
	private String rL01;

	@Column(name="r_l010", length=2)
	private String rL010;

	@Column(name="r_l011", length=2)
	private String rL011;

	@Column(name="r_l012", length=2)
	private String rL012;

	@Column(name="r_l013", length=2)
	private String rL013;

	@Column(name="r_l014", length=2)
	private String rL014;

	@Column(name="r_l015", length=2)
	private String rL015;

	@Column(name="r_l02", length=2)
	private String rL02;

	@Column(name="r_l03", length=2)
	private String rL03;

	@Column(name="r_l04", length=2)
	private String rL04;

	@Column(name="r_l05", length=2)
	private String rL05;

	@Column(name="r_l06", length=2)
	private String rL06;

	@Column(name="r_l07", length=2)
	private String rL07;

	@Column(name="r_l08", length=2)
	private String rL08;

	@Column(name="r_l09", length=2)
	private String rL09;

	@Column(name="r_l101", length=2)
	private String rL101;

	@Column(name="r_l1010", length=2)
	private String rL1010;

	@Column(name="r_l1011", length=2)
	private String rL1011;

	@Column(name="r_l1012", length=2)
	private String rL1012;

	@Column(name="r_l1013", length=2)
	private String rL1013;

	@Column(name="r_l1014", length=2)
	private String rL1014;

	@Column(name="r_l1015", length=2)
	private String rL1015;

	@Column(name="r_l102", length=2)
	private String rL102;

	@Column(name="r_l103", length=2)
	private String rL103;

	@Column(name="r_l104", length=2)
	private String rL104;

	@Column(name="r_l105", length=2)
	private String rL105;

	@Column(name="r_l106", length=2)
	private String rL106;

	@Column(name="r_l107", length=2)
	private String rL107;

	@Column(name="r_l108", length=2)
	private String rL108;

	@Column(name="r_l109", length=2)
	private String rL109;

	@Column(name="r_l11", length=2)
	private String rL11;

	@Column(name="r_l11_1", length=2)
	private String rL11_1;

	@Column(name="r_l11_10", length=2)
	private String rL11_10;

	@Column(name="r_l11_11", length=2)
	private String rL11_11;

	@Column(name="r_l11_12", length=2)
	private String rL11_12;

	@Column(name="r_l11_13", length=2)
	private String rL11_13;

	@Column(name="r_l11_14", length=2)
	private String rL11_14;

	@Column(name="r_l11_15", length=2)
	private String rL11_15;

	@Column(name="r_l11_2", length=2)
	private String rL11_2;

	@Column(name="r_l11_3", length=2)
	private String rL11_3;

	@Column(name="r_l11_4", length=2)
	private String rL11_4;

	@Column(name="r_l11_5", length=2)
	private String rL11_5;

	@Column(name="r_l11_6", length=2)
	private String rL11_6;

	@Column(name="r_l11_7", length=2)
	private String rL11_7;

	@Column(name="r_l11_8", length=2)
	private String rL11_8;

	@Column(name="r_l11_9", length=2)
	private String rL11_9;

	@Column(name="r_l110", length=2)
	private String rL110;

	@Column(name="r_l111", length=2)
	private String rL111;

	@Column(name="r_l112", length=2)
	private String rL112;

	@Column(name="r_l113", length=2)
	private String rL113;

	@Column(name="r_l114", length=2)
	private String rL114;

	@Column(name="r_l115", length=2)
	private String rL115;

	@Column(name="r_l12", length=2)
	private String rL12;

	@Column(name="r_l121", length=2)
	private String rL121;

	@Column(name="r_l1210", length=2)
	private String rL1210;

	@Column(name="r_l1211", length=2)
	private String rL1211;

	@Column(name="r_l1212", length=2)
	private String rL1212;

	@Column(name="r_l1213", length=2)
	private String rL1213;

	@Column(name="r_l1214", length=2)
	private String rL1214;

	@Column(name="r_l1215", length=2)
	private String rL1215;

	@Column(name="r_l122", length=2)
	private String rL122;

	@Column(name="r_l123", length=2)
	private String rL123;

	@Column(name="r_l124", length=2)
	private String rL124;

	@Column(name="r_l125", length=2)
	private String rL125;

	@Column(name="r_l126", length=2)
	private String rL126;

	@Column(name="r_l127", length=2)
	private String rL127;

	@Column(name="r_l128", length=2)
	private String rL128;

	@Column(name="r_l129", length=2)
	private String rL129;

	@Column(name="r_l13", length=2)
	private String rL13;

	@Column(name="r_l131", length=2)
	private String rL131;

	@Column(name="r_l1310", length=2)
	private String rL1310;

	@Column(name="r_l1311", length=2)
	private String rL1311;

	@Column(name="r_l1312", length=2)
	private String rL1312;

	@Column(name="r_l1313", length=2)
	private String rL1313;

	@Column(name="r_l1314", length=2)
	private String rL1314;

	@Column(name="r_l1315", length=2)
	private String rL1315;

	@Column(name="r_l132", length=2)
	private String rL132;

	@Column(name="r_l133", length=2)
	private String rL133;

	@Column(name="r_l134", length=2)
	private String rL134;

	@Column(name="r_l135", length=2)
	private String rL135;

	@Column(name="r_l136", length=2)
	private String rL136;

	@Column(name="r_l137", length=2)
	private String rL137;

	@Column(name="r_l138", length=2)
	private String rL138;

	@Column(name="r_l139", length=2)
	private String rL139;

	@Column(name="r_l14", length=2)
	private String rL14;

	@Column(name="r_l141", length=2)
	private String rL141;

	@Column(name="r_l1410", length=2)
	private String rL1410;

	@Column(name="r_l1411", length=2)
	private String rL1411;

	@Column(name="r_l1412", length=2)
	private String rL1412;

	@Column(name="r_l1413", length=2)
	private String rL1413;

	@Column(name="r_l1414", length=2)
	private String rL1414;

	@Column(name="r_l1415", length=2)
	private String rL1415;

	@Column(name="r_l142", length=2)
	private String rL142;

	@Column(name="r_l143", length=2)
	private String rL143;

	@Column(name="r_l144", length=2)
	private String rL144;

	@Column(name="r_l145", length=2)
	private String rL145;

	@Column(name="r_l146", length=2)
	private String rL146;

	@Column(name="r_l147", length=2)
	private String rL147;

	@Column(name="r_l148", length=2)
	private String rL148;

	@Column(name="r_l149", length=2)
	private String rL149;

	@Column(name="r_l15", length=2)
	private String rL15;

	@Column(name="r_l151", length=2)
	private String rL151;

	@Column(name="r_l1510", length=2)
	private String rL1510;

	@Column(name="r_l1511", length=2)
	private String rL1511;

	@Column(name="r_l1512", length=2)
	private String rL1512;

	@Column(name="r_l1513", length=2)
	private String rL1513;

	@Column(name="r_l1514", length=2)
	private String rL1514;

	@Column(name="r_l1515", length=2)
	private String rL1515;

	@Column(name="r_l152", length=2)
	private String rL152;

	@Column(name="r_l153", length=2)
	private String rL153;

	@Column(name="r_l154", length=2)
	private String rL154;

	@Column(name="r_l155", length=2)
	private String rL155;

	@Column(name="r_l156", length=2)
	private String rL156;

	@Column(name="r_l157", length=2)
	private String rL157;

	@Column(name="r_l158", length=2)
	private String rL158;

	@Column(name="r_l159", length=2)
	private String rL159;

	@Column(name="r_l16", length=2)
	private String rL16;

	@Column(name="r_l161", length=2)
	private String rL161;

	@Column(name="r_l1610", length=2)
	private String rL1610;

	@Column(name="r_l1611", length=2)
	private String rL1611;

	@Column(name="r_l1612", length=2)
	private String rL1612;

	@Column(name="r_l1613", length=2)
	private String rL1613;

	@Column(name="r_l1614", length=2)
	private String rL1614;

	@Column(name="r_l1615", length=2)
	private String rL1615;

	@Column(name="r_l162", length=2)
	private String rL162;

	@Column(name="r_l163", length=2)
	private String rL163;

	@Column(name="r_l164", length=2)
	private String rL164;

	@Column(name="r_l165", length=2)
	private String rL165;

	@Column(name="r_l166", length=2)
	private String rL166;

	@Column(name="r_l167", length=2)
	private String rL167;

	@Column(name="r_l168", length=2)
	private String rL168;

	@Column(name="r_l169", length=2)
	private String rL169;

	@Column(name="r_l17", length=2)
	private String rL17;

	@Column(name="r_l171", length=2)
	private String rL171;

	@Column(name="r_l1710", length=2)
	private String rL1710;

	@Column(name="r_l1711", length=2)
	private String rL1711;

	@Column(name="r_l1712", length=2)
	private String rL1712;

	@Column(name="r_l1713", length=2)
	private String rL1713;

	@Column(name="r_l1714", length=2)
	private String rL1714;

	@Column(name="r_l1715", length=2)
	private String rL1715;

	@Column(name="r_l172", length=2)
	private String rL172;

	@Column(name="r_l173", length=2)
	private String rL173;

	@Column(name="r_l174", length=2)
	private String rL174;

	@Column(name="r_l175", length=2)
	private String rL175;

	@Column(name="r_l176", length=2)
	private String rL176;

	@Column(name="r_l177", length=2)
	private String rL177;

	@Column(name="r_l178", length=2)
	private String rL178;

	@Column(name="r_l179", length=2)
	private String rL179;

	@Column(name="r_l18", length=2)
	private String rL18;

	@Column(name="r_l181", length=2)
	private String rL181;

	@Column(name="r_l1810", length=2)
	private String rL1810;

	@Column(name="r_l1811", length=2)
	private String rL1811;

	@Column(name="r_l1812", length=2)
	private String rL1812;

	@Column(name="r_l1813", length=2)
	private String rL1813;

	@Column(name="r_l1814", length=2)
	private String rL1814;

	@Column(name="r_l1815", length=2)
	private String rL1815;

	@Column(name="r_l182", length=2)
	private String rL182;

	@Column(name="r_l183", length=2)
	private String rL183;

	@Column(name="r_l184", length=2)
	private String rL184;

	@Column(name="r_l185", length=2)
	private String rL185;

	@Column(name="r_l186", length=2)
	private String rL186;

	@Column(name="r_l187", length=2)
	private String rL187;

	@Column(name="r_l188", length=2)
	private String rL188;

	@Column(name="r_l189", length=2)
	private String rL189;

	@Column(name="r_l19", length=2)
	private String rL19;

	@Column(name="r_l191", length=2)
	private String rL191;

	@Column(name="r_l1910", length=2)
	private String rL1910;

	@Column(name="r_l1911", length=2)
	private String rL1911;

	@Column(name="r_l1912", length=2)
	private String rL1912;

	@Column(name="r_l1913", length=2)
	private String rL1913;

	@Column(name="r_l1914", length=2)
	private String rL1914;

	@Column(name="r_l1915", length=2)
	private String rL1915;

	@Column(name="r_l192", length=2)
	private String rL192;

	@Column(name="r_l193", length=2)
	private String rL193;

	@Column(name="r_l194", length=2)
	private String rL194;

	@Column(name="r_l195", length=2)
	private String rL195;

	@Column(name="r_l196", length=2)
	private String rL196;

	@Column(name="r_l197", length=2)
	private String rL197;

	@Column(name="r_l198", length=2)
	private String rL198;

	@Column(name="r_l199", length=2)
	private String rL199;

	@Column(name="r_l201", length=2)
	private String rL201;

	@Column(name="r_l2010", length=2)
	private String rL2010;

	@Column(name="r_l2011", length=2)
	private String rL2011;

	@Column(name="r_l2012", length=2)
	private String rL2012;

	@Column(name="r_l2013", length=2)
	private String rL2013;

	@Column(name="r_l2014", length=2)
	private String rL2014;

	@Column(name="r_l2015", length=2)
	private String rL2015;

	@Column(name="r_l202", length=2)
	private String rL202;

	@Column(name="r_l203", length=2)
	private String rL203;

	@Column(name="r_l204", length=2)
	private String rL204;

	@Column(name="r_l205", length=2)
	private String rL205;

	@Column(name="r_l206", length=2)
	private String rL206;

	@Column(name="r_l207", length=2)
	private String rL207;

	@Column(name="r_l208", length=2)
	private String rL208;

	@Column(name="r_l209", length=2)
	private String rL209;

	@Column(name="r_l21", length=2)
	private String rL21;

	@Column(name="r_l21_1", length=2)
	private String rL21_1;

	@Column(name="r_l21_10", length=2)
	private String rL21_10;

	@Column(name="r_l21_11", length=2)
	private String rL21_11;

	@Column(name="r_l21_12", length=2)
	private String rL21_12;

	@Column(name="r_l21_13", length=2)
	private String rL21_13;

	@Column(name="r_l21_14", length=2)
	private String rL21_14;

	@Column(name="r_l21_15", length=2)
	private String rL21_15;

	@Column(name="r_l21_2", length=2)
	private String rL21_2;

	@Column(name="r_l21_3", length=2)
	private String rL21_3;

	@Column(name="r_l21_4", length=2)
	private String rL21_4;

	@Column(name="r_l21_5", length=2)
	private String rL21_5;

	@Column(name="r_l21_6", length=2)
	private String rL21_6;

	@Column(name="r_l21_7", length=2)
	private String rL21_7;

	@Column(name="r_l21_8", length=2)
	private String rL21_8;

	@Column(name="r_l21_9", length=2)
	private String rL21_9;

	@Column(name="r_l210", length=2)
	private String rL210;

	@Column(name="r_l211", length=2)
	private String rL211;

	@Column(name="r_l212", length=2)
	private String rL212;

	@Column(name="r_l213", length=2)
	private String rL213;

	@Column(name="r_l214", length=2)
	private String rL214;

	@Column(name="r_l215", length=2)
	private String rL215;

	@Column(name="r_l22", length=2)
	private String rL22;

	@Column(name="r_l221", length=2)
	private String rL221;

	@Column(name="r_l2210", length=2)
	private String rL2210;

	@Column(name="r_l2211", length=2)
	private String rL2211;

	@Column(name="r_l2212", length=2)
	private String rL2212;

	@Column(name="r_l2213", length=2)
	private String rL2213;

	@Column(name="r_l2214", length=2)
	private String rL2214;

	@Column(name="r_l2215", length=2)
	private String rL2215;

	@Column(name="r_l222", length=2)
	private String rL222;

	@Column(name="r_l223", length=2)
	private String rL223;

	@Column(name="r_l224", length=2)
	private String rL224;

	@Column(name="r_l225", length=2)
	private String rL225;

	@Column(name="r_l226", length=2)
	private String rL226;

	@Column(name="r_l227", length=2)
	private String rL227;

	@Column(name="r_l228", length=2)
	private String rL228;

	@Column(name="r_l229", length=2)
	private String rL229;

	@Column(name="r_l23", length=2)
	private String rL23;

	@Column(name="r_l231", length=2)
	private String rL231;

	@Column(name="r_l2310", length=2)
	private String rL2310;

	@Column(name="r_l2311", length=2)
	private String rL2311;

	@Column(name="r_l2312", length=2)
	private String rL2312;

	@Column(name="r_l2313", length=2)
	private String rL2313;

	@Column(name="r_l2314", length=2)
	private String rL2314;

	@Column(name="r_l2315", length=2)
	private String rL2315;

	@Column(name="r_l232", length=2)
	private String rL232;

	@Column(name="r_l233", length=2)
	private String rL233;

	@Column(name="r_l234", length=2)
	private String rL234;

	@Column(name="r_l235", length=2)
	private String rL235;

	@Column(name="r_l236", length=2)
	private String rL236;

	@Column(name="r_l237", length=2)
	private String rL237;

	@Column(name="r_l238", length=2)
	private String rL238;

	@Column(name="r_l239", length=2)
	private String rL239;

	@Column(name="r_l24", length=2)
	private String rL24;

	@Column(name="r_l241", length=2)
	private String rL241;

	@Column(name="r_l2410", length=2)
	private String rL2410;

	@Column(name="r_l2411", length=2)
	private String rL2411;

	@Column(name="r_l2412", length=2)
	private String rL2412;

	@Column(name="r_l2413", length=2)
	private String rL2413;

	@Column(name="r_l2414", length=2)
	private String rL2414;

	@Column(name="r_l2415", length=2)
	private String rL2415;

	@Column(name="r_l242", length=2)
	private String rL242;

	@Column(name="r_l243", length=2)
	private String rL243;

	@Column(name="r_l244", length=2)
	private String rL244;

	@Column(name="r_l245", length=2)
	private String rL245;

	@Column(name="r_l246", length=2)
	private String rL246;

	@Column(name="r_l247", length=2)
	private String rL247;

	@Column(name="r_l248", length=2)
	private String rL248;

	@Column(name="r_l249", length=2)
	private String rL249;

	@Column(name="r_l25", length=2)
	private String rL25;

	@Column(name="r_l251", length=2)
	private String rL251;

	@Column(name="r_l2510", length=2)
	private String rL2510;

	@Column(name="r_l2511", length=2)
	private String rL2511;

	@Column(name="r_l2512", length=2)
	private String rL2512;

	@Column(name="r_l2513", length=2)
	private String rL2513;

	@Column(name="r_l2514", length=2)
	private String rL2514;

	@Column(name="r_l2515", length=2)
	private String rL2515;

	@Column(name="r_l252", length=2)
	private String rL252;

	@Column(name="r_l253", length=2)
	private String rL253;

	@Column(name="r_l254", length=2)
	private String rL254;

	@Column(name="r_l255", length=2)
	private String rL255;

	@Column(name="r_l256", length=2)
	private String rL256;

	@Column(name="r_l257", length=2)
	private String rL257;

	@Column(name="r_l258", length=2)
	private String rL258;

	@Column(name="r_l259", length=2)
	private String rL259;

	@Column(name="r_l26", length=2)
	private String rL26;

	@Column(name="r_l261", length=2)
	private String rL261;

	@Column(name="r_l2610", length=2)
	private String rL2610;

	@Column(name="r_l2611", length=2)
	private String rL2611;

	@Column(name="r_l2612", length=2)
	private String rL2612;

	@Column(name="r_l2613", length=2)
	private String rL2613;

	@Column(name="r_l2614", length=2)
	private String rL2614;

	@Column(name="r_l2615", length=2)
	private String rL2615;

	@Column(name="r_l262", length=2)
	private String rL262;

	@Column(name="r_l263", length=2)
	private String rL263;

	@Column(name="r_l264", length=2)
	private String rL264;

	@Column(name="r_l265", length=2)
	private String rL265;

	@Column(name="r_l266", length=2)
	private String rL266;

	@Column(name="r_l267", length=2)
	private String rL267;

	@Column(name="r_l268", length=2)
	private String rL268;

	@Column(name="r_l269", length=2)
	private String rL269;

	@Column(name="r_l27", length=2)
	private String rL27;

	@Column(name="r_l271", length=2)
	private String rL271;

	@Column(name="r_l2710", length=2)
	private String rL2710;

	@Column(name="r_l2711", length=2)
	private String rL2711;

	@Column(name="r_l2712", length=2)
	private String rL2712;

	@Column(name="r_l2713", length=2)
	private String rL2713;

	@Column(name="r_l2714", length=2)
	private String rL2714;

	@Column(name="r_l2715", length=2)
	private String rL2715;

	@Column(name="r_l272", length=2)
	private String rL272;

	@Column(name="r_l273", length=2)
	private String rL273;

	@Column(name="r_l274", length=2)
	private String rL274;

	@Column(name="r_l275", length=2)
	private String rL275;

	@Column(name="r_l276", length=2)
	private String rL276;

	@Column(name="r_l277", length=2)
	private String rL277;

	@Column(name="r_l278", length=2)
	private String rL278;

	@Column(name="r_l279", length=2)
	private String rL279;

	@Column(name="r_l28", length=2)
	private String rL28;

	@Column(name="r_l281", length=2)
	private String rL281;

	@Column(name="r_l2810", length=2)
	private String rL2810;

	@Column(name="r_l2811", length=2)
	private String rL2811;

	@Column(name="r_l2812", length=2)
	private String rL2812;

	@Column(name="r_l2813", length=2)
	private String rL2813;

	@Column(name="r_l2814", length=2)
	private String rL2814;

	@Column(name="r_l2815", length=2)
	private String rL2815;

	@Column(name="r_l282", length=2)
	private String rL282;

	@Column(name="r_l283", length=2)
	private String rL283;

	@Column(name="r_l284", length=2)
	private String rL284;

	@Column(name="r_l285", length=2)
	private String rL285;

	@Column(name="r_l286", length=2)
	private String rL286;

	@Column(name="r_l287", length=2)
	private String rL287;

	@Column(name="r_l288", length=2)
	private String rL288;

	@Column(name="r_l289", length=2)
	private String rL289;

	@Column(name="r_l29", length=2)
	private String rL29;

	@Column(name="r_l291", length=2)
	private String rL291;

	@Column(name="r_l2910", length=2)
	private String rL2910;

	@Column(name="r_l2911", length=2)
	private String rL2911;

	@Column(name="r_l2912", length=2)
	private String rL2912;

	@Column(name="r_l2913", length=2)
	private String rL2913;

	@Column(name="r_l2914", length=2)
	private String rL2914;

	@Column(name="r_l2915", length=2)
	private String rL2915;

	@Column(name="r_l292", length=2)
	private String rL292;

	@Column(name="r_l293", length=2)
	private String rL293;

	@Column(name="r_l294", length=2)
	private String rL294;

	@Column(name="r_l295", length=2)
	private String rL295;

	@Column(name="r_l296", length=2)
	private String rL296;

	@Column(name="r_l297", length=2)
	private String rL297;

	@Column(name="r_l298", length=2)
	private String rL298;

	@Column(name="r_l299", length=2)
	private String rL299;

	@Column(name="r_l301", length=2)
	private String rL301;

	@Column(name="r_l3010", length=2)
	private String rL3010;

	@Column(name="r_l3011", length=2)
	private String rL3011;

	@Column(name="r_l3012", length=2)
	private String rL3012;

	@Column(name="r_l3013", length=2)
	private String rL3013;

	@Column(name="r_l3014", length=2)
	private String rL3014;

	@Column(name="r_l3015", length=2)
	private String rL3015;

	@Column(name="r_l302", length=2)
	private String rL302;

	@Column(name="r_l303", length=2)
	private String rL303;

	@Column(name="r_l304", length=2)
	private String rL304;

	@Column(name="r_l305", length=2)
	private String rL305;

	@Column(name="r_l306", length=2)
	private String rL306;

	@Column(name="r_l307", length=2)
	private String rL307;

	@Column(name="r_l308", length=2)
	private String rL308;

	@Column(name="r_l309", length=2)
	private String rL309;

	@Column(name="r_l31", length=2)
	private String rL31;

	@Column(name="r_l31_1", length=2)
	private String rL31_1;

	@Column(name="r_l31_10", length=2)
	private String rL31_10;

	@Column(name="r_l31_11", length=2)
	private String rL31_11;

	@Column(name="r_l31_12", length=2)
	private String rL31_12;

	@Column(name="r_l31_13", length=2)
	private String rL31_13;

	@Column(name="r_l31_14", length=2)
	private String rL31_14;

	@Column(name="r_l31_15", length=2)
	private String rL31_15;

	@Column(name="r_l31_2", length=2)
	private String rL31_2;

	@Column(name="r_l31_3", length=2)
	private String rL31_3;

	@Column(name="r_l31_4", length=2)
	private String rL31_4;

	@Column(name="r_l31_5", length=2)
	private String rL31_5;

	@Column(name="r_l31_6", length=2)
	private String rL31_6;

	@Column(name="r_l31_7", length=2)
	private String rL31_7;

	@Column(name="r_l31_8", length=2)
	private String rL31_8;

	@Column(name="r_l31_9", length=2)
	private String rL31_9;

	@Column(name="r_l310", length=2)
	private String rL310;

	@Column(name="r_l311", length=2)
	private String rL311;

	@Column(name="r_l312", length=2)
	private String rL312;

	@Column(name="r_l313", length=2)
	private String rL313;

	@Column(name="r_l314", length=2)
	private String rL314;

	@Column(name="r_l315", length=2)
	private String rL315;

	@Column(name="r_l32", length=2)
	private String rL32;

	@Column(name="r_l321", length=2)
	private String rL321;

	@Column(name="r_l3210", length=2)
	private String rL3210;

	@Column(name="r_l3211", length=2)
	private String rL3211;

	@Column(name="r_l3212", length=2)
	private String rL3212;

	@Column(name="r_l3213", length=2)
	private String rL3213;

	@Column(name="r_l3214", length=2)
	private String rL3214;

	@Column(name="r_l3215", length=2)
	private String rL3215;

	@Column(name="r_l322", length=2)
	private String rL322;

	@Column(name="r_l323", length=2)
	private String rL323;

	@Column(name="r_l324", length=2)
	private String rL324;

	@Column(name="r_l325", length=2)
	private String rL325;

	@Column(name="r_l326", length=2)
	private String rL326;

	@Column(name="r_l327", length=2)
	private String rL327;

	@Column(name="r_l328", length=2)
	private String rL328;

	@Column(name="r_l329", length=2)
	private String rL329;

	@Column(name="r_l33", length=2)
	private String rL33;

	@Column(name="r_l331", length=2)
	private String rL331;

	@Column(name="r_l3310", length=2)
	private String rL3310;

	@Column(name="r_l3311", length=2)
	private String rL3311;

	@Column(name="r_l3312", length=2)
	private String rL3312;

	@Column(name="r_l3313", length=2)
	private String rL3313;

	@Column(name="r_l3314", length=2)
	private String rL3314;

	@Column(name="r_l3315", length=2)
	private String rL3315;

	@Column(name="r_l332", length=2)
	private String rL332;

	@Column(name="r_l333", length=2)
	private String rL333;

	@Column(name="r_l334", length=2)
	private String rL334;

	@Column(name="r_l335", length=2)
	private String rL335;

	@Column(name="r_l336", length=2)
	private String rL336;

	@Column(name="r_l337", length=2)
	private String rL337;

	@Column(name="r_l338", length=2)
	private String rL338;

	@Column(name="r_l339", length=2)
	private String rL339;

	@Column(name="r_l34", length=2)
	private String rL34;

	@Column(name="r_l341", length=2)
	private String rL341;

	@Column(name="r_l3410", length=2)
	private String rL3410;

	@Column(name="r_l3411", length=2)
	private String rL3411;

	@Column(name="r_l3412", length=2)
	private String rL3412;

	@Column(name="r_l3413", length=2)
	private String rL3413;

	@Column(name="r_l3414", length=2)
	private String rL3414;

	@Column(name="r_l3415", length=2)
	private String rL3415;

	@Column(name="r_l342", length=2)
	private String rL342;

	@Column(name="r_l343", length=2)
	private String rL343;

	@Column(name="r_l344", length=2)
	private String rL344;

	@Column(name="r_l345", length=2)
	private String rL345;

	@Column(name="r_l346", length=2)
	private String rL346;

	@Column(name="r_l347", length=2)
	private String rL347;

	@Column(name="r_l348", length=2)
	private String rL348;

	@Column(name="r_l349", length=2)
	private String rL349;

	@Column(name="r_l35", length=2)
	private String rL35;

	@Column(name="r_l351", length=2)
	private String rL351;

	@Column(name="r_l3510", length=2)
	private String rL3510;

	@Column(name="r_l3511", length=2)
	private String rL3511;

	@Column(name="r_l3512", length=2)
	private String rL3512;

	@Column(name="r_l3513", length=2)
	private String rL3513;

	@Column(name="r_l3514", length=2)
	private String rL3514;

	@Column(name="r_l3515", length=2)
	private String rL3515;

	@Column(name="r_l352", length=2)
	private String rL352;

	@Column(name="r_l353", length=2)
	private String rL353;

	@Column(name="r_l354", length=2)
	private String rL354;

	@Column(name="r_l355", length=2)
	private String rL355;

	@Column(name="r_l356", length=2)
	private String rL356;

	@Column(name="r_l357", length=2)
	private String rL357;

	@Column(name="r_l358", length=2)
	private String rL358;

	@Column(name="r_l359", length=2)
	private String rL359;

	@Column(name="r_l36", length=2)
	private String rL36;

	@Column(name="r_l37", length=2)
	private String rL37;

	@Column(name="r_l38", length=2)
	private String rL38;

	@Column(name="r_l39", length=2)
	private String rL39;

	@Column(name="r_l41", length=2)
	private String rL41;

	@Column(name="r_l410", length=2)
	private String rL410;

	@Column(name="r_l411", length=2)
	private String rL411;

	@Column(name="r_l412", length=2)
	private String rL412;

	@Column(name="r_l413", length=2)
	private String rL413;

	@Column(name="r_l414", length=2)
	private String rL414;

	@Column(name="r_l415", length=2)
	private String rL415;

	@Column(name="r_l42", length=2)
	private String rL42;

	@Column(name="r_l43", length=2)
	private String rL43;

	@Column(name="r_l44", length=2)
	private String rL44;

	@Column(name="r_l45", length=2)
	private String rL45;

	@Column(name="r_l46", length=2)
	private String rL46;

	@Column(name="r_l47", length=2)
	private String rL47;

	@Column(name="r_l48", length=2)
	private String rL48;

	@Column(name="r_l49", length=2)
	private String rL49;

	@Column(name="r_l51", length=2)
	private String rL51;

	@Column(name="r_l510", length=2)
	private String rL510;

	@Column(name="r_l511", length=2)
	private String rL511;

	@Column(name="r_l512", length=2)
	private String rL512;

	@Column(name="r_l513", length=2)
	private String rL513;

	@Column(name="r_l514", length=2)
	private String rL514;

	@Column(name="r_l515", length=2)
	private String rL515;

	@Column(name="r_l52", length=2)
	private String rL52;

	@Column(name="r_l53", length=2)
	private String rL53;

	@Column(name="r_l54", length=2)
	private String rL54;

	@Column(name="r_l55", length=2)
	private String rL55;

	@Column(name="r_l56", length=2)
	private String rL56;

	@Column(name="r_l57", length=2)
	private String rL57;

	@Column(name="r_l58", length=2)
	private String rL58;

	@Column(name="r_l59", length=2)
	private String rL59;

	@Column(name="r_l61", length=2)
	private String rL61;

	@Column(name="r_l610", length=2)
	private String rL610;

	@Column(name="r_l611", length=2)
	private String rL611;

	@Column(name="r_l612", length=2)
	private String rL612;

	@Column(name="r_l613", length=2)
	private String rL613;

	@Column(name="r_l614", length=2)
	private String rL614;

	@Column(name="r_l615", length=2)
	private String rL615;

	@Column(name="r_l62", length=2)
	private String rL62;

	@Column(name="r_l63", length=2)
	private String rL63;

	@Column(name="r_l64", length=2)
	private String rL64;

	@Column(name="r_l65", length=2)
	private String rL65;

	@Column(name="r_l66", length=2)
	private String rL66;

	@Column(name="r_l67", length=2)
	private String rL67;

	@Column(name="r_l68", length=2)
	private String rL68;

	@Column(name="r_l69", length=2)
	private String rL69;

	@Column(name="r_l71", length=2)
	private String rL71;

	@Column(name="r_l710", length=2)
	private String rL710;

	@Column(name="r_l711", length=2)
	private String rL711;

	@Column(name="r_l712", length=2)
	private String rL712;

	@Column(name="r_l713", length=2)
	private String rL713;

	@Column(name="r_l714", length=2)
	private String rL714;

	@Column(name="r_l715", length=2)
	private String rL715;

	@Column(name="r_l72", length=2)
	private String rL72;

	@Column(name="r_l73", length=2)
	private String rL73;

	@Column(name="r_l74", length=2)
	private String rL74;

	@Column(name="r_l75", length=2)
	private String rL75;

	@Column(name="r_l76", length=2)
	private String rL76;

	@Column(name="r_l77", length=2)
	private String rL77;

	@Column(name="r_l78", length=2)
	private String rL78;

	@Column(name="r_l79", length=2)
	private String rL79;

	@Column(name="r_l81", length=2)
	private String rL81;

	@Column(name="r_l810", length=2)
	private String rL810;

	@Column(name="r_l811", length=2)
	private String rL811;

	@Column(name="r_l812", length=2)
	private String rL812;

	@Column(name="r_l813", length=2)
	private String rL813;

	@Column(name="r_l814", length=2)
	private String rL814;

	@Column(name="r_l815", length=2)
	private String rL815;

	@Column(name="r_l82", length=2)
	private String rL82;

	@Column(name="r_l83", length=2)
	private String rL83;

	@Column(name="r_l84", length=2)
	private String rL84;

	@Column(name="r_l85", length=2)
	private String rL85;

	@Column(name="r_l86", length=2)
	private String rL86;

	@Column(name="r_l87", length=2)
	private String rL87;

	@Column(name="r_l88", length=2)
	private String rL88;

	@Column(name="r_l89", length=2)
	private String rL89;

	@Column(name="r_l91", length=2)
	private String rL91;

	@Column(name="r_l910", length=2)
	private String rL910;

	@Column(name="r_l911", length=2)
	private String rL911;

	@Column(name="r_l912", length=2)
	private String rL912;

	@Column(name="r_l913", length=2)
	private String rL913;

	@Column(name="r_l914", length=2)
	private String rL914;

	@Column(name="r_l915", length=2)
	private String rL915;

	@Column(name="r_l92", length=2)
	private String rL92;

	@Column(name="r_l93", length=2)
	private String rL93;

	@Column(name="r_l94", length=2)
	private String rL94;

	@Column(name="r_l95", length=2)
	private String rL95;

	@Column(name="r_l96", length=2)
	private String rL96;

	@Column(name="r_l97", length=2)
	private String rL97;

	@Column(name="r_l98", length=2)
	private String rL98;

	@Column(name="r_l99", length=2)
	private String rL99;

	@Column(length=7)
	private String sonographer1;

	@Column(length=7)
	private String sonographer10;

	@Column(length=7)
	private String sonographer11;

	@Column(length=7)
	private String sonographer12;

	@Column(length=7)
	private String sonographer13;

	@Column(length=7)
	private String sonographer14;

	@Column(length=7)
	private String sonographer15;

	@Column(length=7)
	private String sonographer16;

	@Column(length=7)
	private String sonographer17;

	@Column(length=7)
	private String sonographer18;

	@Column(length=7)
	private String sonographer2;

	@Column(length=7)
	private String sonographer3;

	@Column(length=7)
	private String sonographer4;

	@Column(length=7)
	private String sonographer5;

	@Column(length=7)
	private String sonographer6;

	@Column(length=7)
	private String sonographer7;

	@Column(length=7)
	private String sonographer8;

	@Column(length=7)
	private String sonographer9;

	@Column(length=6)
	private String tsh;
	
	@Column(length=6)
	private String amh;

	@Column(length=127)
	private String txt1;

	@Column(length=255)
	private String txt2;

//	private IvfTrackSheetDAO ivfTrackSheetDao;
	
	public FormIvfTrackSheet() {
	}

//	public IvfTrackSheetDAO getIvfTrackSheetDao() {
//		return ivfTrackSheetDao;
//	}
//
//	public void setIvfTrackSheetDao(IvfTrackSheetDAO ivfTrackSheetDao) {
//		this.ivfTrackSheetDao = ivfTrackSheetDao;
//	}

	public Integer getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getATxt() {
		return this.aTxt;
	}

	public void setATxt(String aTxt) {
		this.aTxt = aTxt;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public byte getAntibiotics() {
		return this.antibiotics;
	}

	public void setAntibiotics(byte antibiotics) {
		this.antibiotics = antibiotics;
	}

	public byte getAssisted() {
		return this.assisted;
	}

	public void setAssisted(byte assisted) {
		this.assisted = assisted;
	}

	public byte getBcs() {
		return this.bcs;
	}

	public void setBcs(byte bcs) {
		this.bcs = bcs;
	}

	public byte getBlastocyst() {
		return this.blastocyst;
	}

	public void setBlastocyst(byte blastocyst) {
		this.blastocyst = blastocyst;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getComment1() {
		return this.comment1;
	}

	public void setComment1(String comment1) {
		this.comment1 = comment1;
	}

	public byte getConsents() {
		return this.consents;
	}

	public void setConsents(byte consents) {
		this.consents = consents;
	}

	public byte getCrinone() {
		return this.crinone;
	}

	public void setCrinone(byte crinone) {
		this.crinone = crinone;
	}

	public String getDate1() {
		return this.date1;
	}

	public void setDate1(String date1) {
		this.date1 = date1;
	}

	public String getDate10() {
		return this.date10;
	}

	public void setDate10(String date10) {
		this.date10 = date10;
	}

	public String getDate11() {
		return this.date11;
	}

	public void setDate11(String date11) {
		this.date11 = date11;
	}

	public String getDate12() {
		return this.date12;
	}

	public void setDate12(String date12) {
		this.date12 = date12;
	}

	public String getDate13() {
		return this.date13;
	}

	public void setDate13(String date13) {
		this.date13 = date13;
	}

	public String getDate14() {
		return this.date14;
	}

	public void setDate14(String date14) {
		this.date14 = date14;
	}

	public String getDate15() {
		return this.date15;
	}

	public void setDate15(String date15) {
		this.date15 = date15;
	}

	public String getDate16() {
		return this.date16;
	}

	public void setDate16(String date16) {
		this.date16 = date16;
	}

	public String getDate17() {
		return this.date17;
	}

	public void setDate17(String date17) {
		this.date17 = date17;
	}

	public String getDate18() {
		return this.date18;
	}

	public void setDate18(String date18) {
		this.date18 = date18;
	}
	
	public String getPgb() {
		return pgb;
	}

	public void setPgb(String pgb) {
		this.pgb = pgb;
	}

	public String getDate2() {
		return this.date2;
	}

	public void setDate2(String date2) {
		this.date2 = date2;
	}

	public String getDate3() {
		return this.date3;
	}

	public void setDate3(String date3) {
		this.date3 = date3;
	}

	public String getDate4() {
		return this.date4;
	}

	public void setDate4(String date4) {
		this.date4 = date4;
	}

	public String getDate5() {
		return this.date5;
	}

	public void setDate5(String date5) {
		this.date5 = date5;
	}

	public String getDate6() {
		return this.date6;
	}

	public void setDate6(String date6) {
		this.date6 = date6;
	}

	public String getDate7() {
		return this.date7;
	}

	public void setDate7(String date7) {
		this.date7 = date7;
	}

	public String getDate8() {
		return this.date8;
	}

	public void setDate8(String date8) {
		this.date8 = date8;
	}

	public String getDate9() {
		return this.date9;
	}

	public void setDate9(String date9) {
		this.date9 = date9;
	}

	public int getDemographicNo() {
		return this.demographicNo;
	}

	public void setDemographicNo(int demographicNo) {
		this.demographicNo = demographicNo;
	}

	public String getDiagnosis() {
		return this.diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getDob() {
		return this.dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}
	
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public byte getDonor() {
		return this.donor;
	}

	public void setDonor(byte donor) {
		this.donor = donor;
	}

	public String getEndometrial1() {
		return this.endometrial1;
	}

	public void setEndometrial1(String endometrial1) {
		this.endometrial1 = endometrial1;
	}

	public String getEndometrial10() {
		return this.endometrial10;
	}

	public void setEndometrial10(String endometrial10) {
		this.endometrial10 = endometrial10;
	}

	public String getEndometrial11() {
		return this.endometrial11;
	}

	public void setEndometrial11(String endometrial11) {
		this.endometrial11 = endometrial11;
	}

	public String getEndometrial12() {
		return this.endometrial12;
	}

	public void setEndometrial12(String endometrial12) {
		this.endometrial12 = endometrial12;
	}

	public String getEndometrial13() {
		return this.endometrial13;
	}

	public void setEndometrial13(String endometrial13) {
		this.endometrial13 = endometrial13;
	}

	public String getEndometrial14() {
		return this.endometrial14;
	}

	public void setEndometrial14(String endometrial14) {
		this.endometrial14 = endometrial14;
	}

	public String getEndometrial15() {
		return this.endometrial15;
	}

	public void setEndometrial15(String endometrial15) {
		this.endometrial15 = endometrial15;
	}

	public String getEndometrial16() {
		return this.endometrial16;
	}

	public void setEndometrial16(String endometrial16) {
		this.endometrial16 = endometrial16;
	}

	public String getEndometrial17() {
		return this.endometrial17;
	}

	public void setEndometrial17(String endometrial17) {
		this.endometrial17 = endometrial17;
	}

	public String getEndometrial18() {
		return this.endometrial18;
	}

	public void setEndometrial18(String endometrial18) {
		this.endometrial18 = endometrial18;
	}

	public String getEndometrial2() {
		return this.endometrial2;
	}

	public void setEndometrial2(String endometrial2) {
		this.endometrial2 = endometrial2;
	}

	public String getEndometrial3() {
		return this.endometrial3;
	}

	public void setEndometrial3(String endometrial3) {
		this.endometrial3 = endometrial3;
	}

	public String getEndometrial4() {
		return this.endometrial4;
	}

	public void setEndometrial4(String endometrial4) {
		this.endometrial4 = endometrial4;
	}

	public String getEndometrial5() {
		return this.endometrial5;
	}

	public void setEndometrial5(String endometrial5) {
		this.endometrial5 = endometrial5;
	}

	public String getEndometrial6() {
		return this.endometrial6;
	}

	public void setEndometrial6(String endometrial6) {
		this.endometrial6 = endometrial6;
	}

	public String getEndometrial7() {
		return this.endometrial7;
	}

	public void setEndometrial7(String endometrial7) {
		this.endometrial7 = endometrial7;
	}

	public String getEndometrial8() {
		return this.endometrial8;
	}

	public void setEndometrial8(String endometrial8) {
		this.endometrial8 = endometrial8;
	}

	public String getEndometrial9() {
		return this.endometrial9;
	}

	public void setEndometrial9(String endometrial9) {
		this.endometrial9 = endometrial9;
	}

	public byte getEndometrin() {
		return this.endometrin;
	}

	public void setEndometrin(byte endometrin) {
		this.endometrin = endometrin;
	}

	public String getEstradiol1() {
		return this.estradiol1;
	}

	public void setEstradiol1(String estradiol1) {
		this.estradiol1 = estradiol1;
	}

	public String getEstradiol10() {
		return this.estradiol10;
	}

	public void setEstradiol10(String estradiol10) {
		this.estradiol10 = estradiol10;
	}

	public String getEstradiol11() {
		return this.estradiol11;
	}

	public void setEstradiol11(String estradiol11) {
		this.estradiol11 = estradiol11;
	}

	public String getEstradiol12() {
		return this.estradiol12;
	}

	public void setEstradiol12(String estradiol12) {
		this.estradiol12 = estradiol12;
	}

	public String getEstradiol13() {
		return this.estradiol13;
	}

	public void setEstradiol13(String estradiol13) {
		this.estradiol13 = estradiol13;
	}

	public String getEstradiol14() {
		return this.estradiol14;
	}

	public void setEstradiol14(String estradiol14) {
		this.estradiol14 = estradiol14;
	}

	public String getEstradiol15() {
		return this.estradiol15;
	}

	public void setEstradiol15(String estradiol15) {
		this.estradiol15 = estradiol15;
	}

	public String getEstradiol16() {
		return this.estradiol16;
	}

	public void setEstradiol16(String estradiol16) {
		this.estradiol16 = estradiol16;
	}

	public String getEstradiol17() {
		return this.estradiol17;
	}

	public void setEstradiol17(String estradiol17) {
		this.estradiol17 = estradiol17;
	}

	public String getEstradiol18() {
		return this.estradiol18;
	}

	public void setEstradiol18(String estradiol18) {
		this.estradiol18 = estradiol18;
	}

	public String getEstradiol2() {
		return this.estradiol2;
	}

	public void setEstradiol2(String estradiol2) {
		this.estradiol2 = estradiol2;
	}

	public String getEstradiol3() {
		return this.estradiol3;
	}

	public void setEstradiol3(String estradiol3) {
		this.estradiol3 = estradiol3;
	}

	public String getEstradiol4() {
		return this.estradiol4;
	}

	public void setEstradiol4(String estradiol4) {
		this.estradiol4 = estradiol4;
	}

	public String getEstradiol5() {
		return this.estradiol5;
	}

	public void setEstradiol5(String estradiol5) {
		this.estradiol5 = estradiol5;
	}

	public String getEstradiol6() {
		return this.estradiol6;
	}

	public void setEstradiol6(String estradiol6) {
		this.estradiol6 = estradiol6;
	}

	public String getEstradiol7() {
		return this.estradiol7;
	}

	public void setEstradiol7(String estradiol7) {
		this.estradiol7 = estradiol7;
	}

	public String getEstradiol8() {
		return this.estradiol8;
	}

	public void setEstradiol8(String estradiol8) {
		this.estradiol8 = estradiol8;
	}

	public String getEstradiol9() {
		return this.estradiol9;
	}

	public void setEstradiol9(String estradiol9) {
		this.estradiol9 = estradiol9;
	}

	public String getForEt() {
		return this.forEt;
	}

	public void setForEt(String forEt) {
		this.forEt = forEt;
	}

	public Date getFormCreated() {
		return this.formCreated;
	}

	public void setFormCreated(Date formCreated) {
		this.formCreated = formCreated;
	}

	public Timestamp getFormEdited() {
		return this.formEdited;
	}

	public void setFormEdited(Timestamp formEdited) {
		this.formEdited = formEdited;
	}

	public byte getFresh() {
		return this.fresh;
	}

	public void setFresh(byte fresh) {
		this.fresh = fresh;
	}

	public byte getFrozen() {
		return this.frozen;
	}

	public void setFrozen(byte frozen) {
		this.frozen = frozen;
	}

	public String getFsh() {
		return this.fsh;
	}

	public void setFsh(String fsh) {
		this.fsh = fsh;
	}

	public byte getFundedN() {
		return this.fundedN;
	}

	public void setFundedN(byte fundedN) {
		this.fundedN = fundedN;
	}

	public byte getFundedY() {
		return this.fundedY;
	}

	public void setFundedY(byte fundedY) {
		this.fundedY = fundedY;
	}

	public String getGTxt() {
		return this.gTxt;
	}

	public void setGTxt(String gTxt) {
		this.gTxt = gTxt;
	}

	public byte getHcg() {
		return this.hcg;
	}

	public void setHcg(byte hcg) {
		this.hcg = hcg;
	}

	public String getHealthNum() {
		return this.healthNum;
	}

	public void setHealthNum(String healthNum) {
		this.healthNum = healthNum;
	}

	public byte getIcsi() {
		return this.icsi;
	}

	public void setIcsi(byte icsi) {
		this.icsi = icsi;
	}

	public byte getIvf() {
		return this.ivf;
	}

	public void setIvf(byte ivf) {
		this.ivf = ivf;
	}

	public String getIvfCycleNumber() {
		return this.ivfCycleNumber;
	}

	public void setIvfCycleNumber(String ivfCycleNumber) {
		this.ivfCycleNumber = ivfCycleNumber;
	}

	public String getLh1() {
		return this.lh1;
	}

	public void setLh1(String lh1) {
		this.lh1 = lh1;
	}

	public String getLh10() {
		return this.lh10;
	}

	public void setLh10(String lh10) {
		this.lh10 = lh10;
	}

	public String getLh11() {
		return this.lh11;
	}

	public void setLh11(String lh11) {
		this.lh11 = lh11;
	}

	public String getLh12() {
		return this.lh12;
	}

	public void setLh12(String lh12) {
		this.lh12 = lh12;
	}

	public String getLh13() {
		return this.lh13;
	}

	public void setLh13(String lh13) {
		this.lh13 = lh13;
	}

	public String getLh14() {
		return this.lh14;
	}

	public void setLh14(String lh14) {
		this.lh14 = lh14;
	}

	public String getLh15() {
		return this.lh15;
	}

	public void setLh15(String lh15) {
		this.lh15 = lh15;
	}

	public String getLh16() {
		return this.lh16;
	}

	public void setLh16(String lh16) {
		this.lh16 = lh16;
	}

	public String getLh17() {
		return this.lh17;
	}

	public void setLh17(String lh17) {
		this.lh17 = lh17;
	}

	public String getLh18() {
		return this.lh18;
	}

	public void setLh18(String lh18) {
		this.lh18 = lh18;
	}

	public String getLh2() {
		return this.lh2;
	}

	public void setLh2(String lh2) {
		this.lh2 = lh2;
	}

	public String getLh3() {
		return this.lh3;
	}

	public void setLh3(String lh3) {
		this.lh3 = lh3;
	}

	public String getLh4() {
		return this.lh4;
	}

	public void setLh4(String lh4) {
		this.lh4 = lh4;
	}

	public String getLh5() {
		return this.lh5;
	}

	public void setLh5(String lh5) {
		this.lh5 = lh5;
	}

	public String getLh6() {
		return this.lh6;
	}

	public void setLh6(String lh6) {
		this.lh6 = lh6;
	}

	public String getLh7() {
		return this.lh7;
	}

	public void setLh7(String lh7) {
		this.lh7 = lh7;
	}

	public String getLh8() {
		return this.lh8;
	}

	public void setLh8(String lh8) {
		this.lh8 = lh8;
	}

	public String getLh9() {
		return this.lh9;
	}

	public void setLh9(String lh9) {
		this.lh9 = lh9;
	}

	public String getMedication() {
		return this.medication;
	}

	public void setMedication(String medication) {
		this.medication = medication;
	}

	public String getMenopur1() {
		return this.menopur1;
	}

	public void setMenopur1(String menopur1) {
		this.menopur1 = menopur1;
	}

	public String getMenopur10() {
		return this.menopur10;
	}

	public void setMenopur10(String menopur10) {
		this.menopur10 = menopur10;
	}

	public String getMenopur11() {
		return this.menopur11;
	}

	public void setMenopur11(String menopur11) {
		this.menopur11 = menopur11;
	}

	public String getMenopur12() {
		return this.menopur12;
	}

	public void setMenopur12(String menopur12) {
		this.menopur12 = menopur12;
	}

	public String getMenopur13() {
		return this.menopur13;
	}

	public void setMenopur13(String menopur13) {
		this.menopur13 = menopur13;
	}

	public String getMenopur14() {
		return this.menopur14;
	}

	public void setMenopur14(String menopur14) {
		this.menopur14 = menopur14;
	}

	public String getMenopur15() {
		return this.menopur15;
	}

	public void setMenopur15(String menopur15) {
		this.menopur15 = menopur15;
	}

	public String getMenopur16() {
		return this.menopur16;
	}

	public void setMenopur16(String menopur16) {
		this.menopur16 = menopur16;
	}

	public String getMenopur17() {
		return this.menopur17;
	}

	public void setMenopur17(String menopur17) {
		this.menopur17 = menopur17;
	}

	public String getMenopur18() {
		return this.menopur18;
	}

	public void setMenopur18(String menopur18) {
		this.menopur18 = menopur18;
	}

	public String getOsc() {
		return osc;
	}

	public void setOsc(String osc) {
		this.osc = osc;
	}

	
	public String getMenopur2() {
		return this.menopur2;
	}

	public void setMenopur2(String menopur2) {
		this.menopur2 = menopur2;
	}

	public String getMenopur3() {
		return this.menopur3;
	}

	public void setMenopur3(String menopur3) {
		this.menopur3 = menopur3;
	}

	public String getMenopur4() {
		return this.menopur4;
	}

	public void setMenopur4(String menopur4) {
		this.menopur4 = menopur4;
	}

	public String getMenopur5() {
		return this.menopur5;
	}

	public void setMenopur5(String menopur5) {
		this.menopur5 = menopur5;
	}

	public String getMenopur6() {
		return this.menopur6;
	}

	public void setMenopur6(String menopur6) {
		this.menopur6 = menopur6;
	}

	public String getMenopur7() {
		return this.menopur7;
	}

	public void setMenopur7(String menopur7) {
		this.menopur7 = menopur7;
	}

	public String getMenopur8() {
		return this.menopur8;
	}

	public void setMenopur8(String menopur8) {
		this.menopur8 = menopur8;
	}

	public String getMenopur9() {
		return this.menopur9;
	}

	public void setMenopur9(String menopur9) {
		this.menopur9 = menopur9;
	}

	public String getOrgalutran1() {
		return this.orgalutran1;
	}

	public void setOrgalutran1(String orgalutran1) {
		this.orgalutran1 = orgalutran1;
	}

	public String getOrgalutran10() {
		return this.orgalutran10;
	}

	public void setOrgalutran10(String orgalutran10) {
		this.orgalutran10 = orgalutran10;
	}

	public String getOrgalutran11() {
		return this.orgalutran11;
	}

	public void setOrgalutran11(String orgalutran11) {
		this.orgalutran11 = orgalutran11;
	}

	public String getOrgalutran12() {
		return this.orgalutran12;
	}

	public void setOrgalutran12(String orgalutran12) {
		this.orgalutran12 = orgalutran12;
	}

	public String getOrgalutran13() {
		return this.orgalutran13;
	}

	public void setOrgalutran13(String orgalutran13) {
		this.orgalutran13 = orgalutran13;
	}

	public String getOrgalutran14() {
		return this.orgalutran14;
	}

	public void setOrgalutran14(String orgalutran14) {
		this.orgalutran14 = orgalutran14;
	}

	public String getOrgalutran15() {
		return this.orgalutran15;
	}

	public void setOrgalutran15(String orgalutran15) {
		this.orgalutran15 = orgalutran15;
	}

	public String getOrgalutran16() {
		return this.orgalutran16;
	}

	public void setOrgalutran16(String orgalutran16) {
		this.orgalutran16 = orgalutran16;
	}

	public String getOrgalutran17() {
		return this.orgalutran17;
	}

	public void setOrgalutran17(String orgalutran17) {
		this.orgalutran17 = orgalutran17;
	}

	public String getOrgalutran18() {
		return this.orgalutran18;
	}

	public void setOrgalutran18(String orgalutran18) {
		this.orgalutran18 = orgalutran18;
	}

	public String getOrgalutran2() {
		return this.orgalutran2;
	}

	public void setOrgalutran2(String orgalutran2) {
		this.orgalutran2 = orgalutran2;
	}

	public String getOrgalutran3() {
		return this.orgalutran3;
	}

	public void setOrgalutran3(String orgalutran3) {
		this.orgalutran3 = orgalutran3;
	}

	public String getOrgalutran4() {
		return this.orgalutran4;
	}

	public void setOrgalutran4(String orgalutran4) {
		this.orgalutran4 = orgalutran4;
	}

	public String getOrgalutran5() {
		return this.orgalutran5;
	}

	public void setOrgalutran5(String orgalutran5) {
		this.orgalutran5 = orgalutran5;
	}

	public String getOrgalutran6() {
		return this.orgalutran6;
	}

	public void setOrgalutran6(String orgalutran6) {
		this.orgalutran6 = orgalutran6;
	}

	public String getOrgalutran7() {
		return this.orgalutran7;
	}

	public void setOrgalutran7(String orgalutran7) {
		this.orgalutran7 = orgalutran7;
	}

	public String getOrgalutran8() {
		return this.orgalutran8;
	}

	public void setOrgalutran8(String orgalutran8) {
		this.orgalutran8 = orgalutran8;
	}

	public String getOrgalutran9() {
		return this.orgalutran9;
	}

	public void setOrgalutran9(String orgalutran9) {
		this.orgalutran9 = orgalutran9;
	}

	public String getPTxt() {
		return this.pTxt;
	}

	public void setPTxt(String pTxt) {
		this.pTxt = pTxt;
	}

	public byte getP4supps() {
		return this.p4supps;
	}

	public void setP4supps(byte p4supps) {
		this.p4supps = p4supps;
	}

	public String getPartnerdob() {
		return this.partnerdob;
	}

	public void setPartnerdob(String partnerdob) {
		this.partnerdob = partnerdob;
	}

	public String getPartnerFirstName() {
		return this.partnerFirstName;
	}

	public void setPartnerFirstName(String partnerFirstName) {
		this.partnerFirstName = partnerFirstName;
	}

	public String getPartnerHealthNum() {
		return this.partnerHealthNum;
	}

	public void setPartnerHealthNum(String partnerHealthNum) {
		this.partnerHealthNum = partnerHealthNum;
	}

	public String getPartnerSurname() {
		return this.partnerSurname;
	}

	public void setPartnerSurname(String partnerSurname) {
		this.partnerSurname = partnerSurname;
	}

	public String getPatientFirstName() {
		return this.patientFirstName;
	}

	public void setPatientFirstName(String patientFirstName) {
		this.patientFirstName = patientFirstName;
	}

	public String getPatientSurname() {
		return this.patientSurname;
	}

	public void setPatientSurname(String patientSurname) {
		this.patientSurname = patientSurname;
	}

	public byte getPesa() {
		return this.pesa;
	}

	public void setPesa(byte pesa) {
		this.pesa = pesa;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhysician() {
		return this.physician;
	}

	public void setPhysician(String physician) {
		this.physician = physician;
	}

	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPreviousfresh() {
		return this.previousfresh;
	}

	public void setPreviousfresh(String previousfresh) {
		this.previousfresh = previousfresh;
	}

	public String getPreviousfrozen() {
		return this.previousfrozen;
	}

	public void setPreviousfrozen(String previousfrozen) {
		this.previousfrozen = previousfrozen;
	}

	public String getPrimaryNurse() {
		return this.primaryNurse;
	}

	public void setPrimaryNurse(String primaryNurse) {
		this.primaryNurse = primaryNurse;
	}

	public String getProges1() {
		return this.proges1;
	}

	public void setProges1(String proges1) {
		this.proges1 = proges1;
	}

	public String getProges10() {
		return this.proges10;
	}

	public void setProges10(String proges10) {
		this.proges10 = proges10;
	}

	public String getProges11() {
		return this.proges11;
	}

	public void setProges11(String proges11) {
		this.proges11 = proges11;
	}

	public String getProges12() {
		return this.proges12;
	}

	public void setProges12(String proges12) {
		this.proges12 = proges12;
	}

	public String getProges13() {
		return this.proges13;
	}

	public void setProges13(String proges13) {
		this.proges13 = proges13;
	}

	public String getProges14() {
		return this.proges14;
	}

	public void setProges14(String proges14) {
		this.proges14 = proges14;
	}

	public String getProges15() {
		return this.proges15;
	}

	public void setProges15(String proges15) {
		this.proges15 = proges15;
	}

	public String getProges16() {
		return this.proges16;
	}

	public void setProges16(String proges16) {
		this.proges16 = proges16;
	}

	public String getProges17() {
		return this.proges17;
	}

	public void setProges17(String proges17) {
		this.proges17 = proges17;
	}

	public String getProges18() {
		return this.proges18;
	}

	public void setProges18(String proges18) {
		this.proges18 = proges18;
	}

	public String getProges2() {
		return this.proges2;
	}

	public void setProges2(String proges2) {
		this.proges2 = proges2;
	}

	public String getProges3() {
		return this.proges3;
	}

	public void setProges3(String proges3) {
		this.proges3 = proges3;
	}

	public String getProges4() {
		return this.proges4;
	}

	public void setProges4(String proges4) {
		this.proges4 = proges4;
	}

	public String getProges5() {
		return this.proges5;
	}

	public void setProges5(String proges5) {
		this.proges5 = proges5;
	}

	public String getProges6() {
		return this.proges6;
	}

	public void setProges6(String proges6) {
		this.proges6 = proges6;
	}

	public String getProges7() {
		return this.proges7;
	}

	public void setProges7(String proges7) {
		this.proges7 = proges7;
	}

	public String getProges8() {
		return this.proges8;
	}

	public void setProges8(String proges8) {
		this.proges8 = proges8;
	}

	public String getProges9() {
		return this.proges9;
	}

	public void setProges9(String proges9) {
		this.proges9 = proges9;
	}

	public int getProviderNo() {
		return this.providerNo;
	}

	public void setProviderNo(int providerNo) {
		this.providerNo = providerNo;
	}

	public String getPuregon1() {
		return this.puregon1;
	}

	public void setPuregon1(String puregon1) {
		this.puregon1 = puregon1;
	}

	public String getPuregon10() {
		return this.puregon10;
	}

	public void setPuregon10(String puregon10) {
		this.puregon10 = puregon10;
	}

	public String getPuregon11() {
		return this.puregon11;
	}

	public void setPuregon11(String puregon11) {
		this.puregon11 = puregon11;
	}

	public String getPuregon12() {
		return this.puregon12;
	}

	public void setPuregon12(String puregon12) {
		this.puregon12 = puregon12;
	}

	public String getPuregon13() {
		return this.puregon13;
	}

	public void setPuregon13(String puregon13) {
		this.puregon13 = puregon13;
	}

	public String getPuregon14() {
		return this.puregon14;
	}

	public void setPuregon14(String puregon14) {
		this.puregon14 = puregon14;
	}

	public String getPuregon15() {
		return this.puregon15;
	}

	public void setPuregon15(String puregon15) {
		this.puregon15 = puregon15;
	}

	public String getPuregon16() {
		return this.puregon16;
	}

	public void setPuregon16(String puregon16) {
		this.puregon16 = puregon16;
	}

	public String getPuregon17() {
		return this.puregon17;
	}

	public void setPuregon17(String puregon17) {
		this.puregon17 = puregon17;
	}

	public String getPuregon18() {
		return this.puregon18;
	}

	public void setPuregon18(String puregon18) {
		this.puregon18 = puregon18;
	}

	public String getMml() {
		return mml;
	}

	public void setMml(String mml) {
		this.mml = mml;
	}
	
	public String getPuregon2() {
		return this.puregon2;
	}

	public void setPuregon2(String puregon2) {
		this.puregon2 = puregon2;
	}

	public String getPuregon3() {
		return this.puregon3;
	}

	public void setPuregon3(String puregon3) {
		this.puregon3 = puregon3;
	}

	public String getPuregon4() {
		return this.puregon4;
	}

	public void setPuregon4(String puregon4) {
		this.puregon4 = puregon4;
	}

	public String getPuregon5() {
		return this.puregon5;
	}

	public void setPuregon5(String puregon5) {
		this.puregon5 = puregon5;
	}

	public String getPuregon6() {
		return this.puregon6;
	}

	public void setPuregon6(String puregon6) {
		this.puregon6 = puregon6;
	}

	public String getPuregon7() {
		return this.puregon7;
	}

	public void setPuregon7(String puregon7) {
		this.puregon7 = puregon7;
	}

	public String getPuregon8() {
		return this.puregon8;
	}

	public void setPuregon8(String puregon8) {
		this.puregon8 = puregon8;
	}

	public String getPuregon9() {
		return this.puregon9;
	}

	public void setPuregon9(String puregon9) {
		this.puregon9 = puregon9;
	}

	public String getRL01() {
		return this.rL01;
	}

	public void setRL01(String rL01) {
		this.rL01 = rL01;
	}

	public String getRL010() {
		return this.rL010;
	}

	public void setRL010(String rL010) {
		this.rL010 = rL010;
	}

	public String getRL011() {
		return this.rL011;
	}

	public void setRL011(String rL011) {
		this.rL011 = rL011;
	}

	public String getRL012() {
		return this.rL012;
	}

	public void setRL012(String rL012) {
		this.rL012 = rL012;
	}

	public String getRL013() {
		return this.rL013;
	}

	public void setRL013(String rL013) {
		this.rL013 = rL013;
	}

	public String getRL014() {
		return this.rL014;
	}

	public void setRL014(String rL014) {
		this.rL014 = rL014;
	}

	public String getRL015() {
		return this.rL015;
	}

	public void setRL015(String rL015) {
		this.rL015 = rL015;
	}

	public String getRL02() {
		return this.rL02;
	}

	public void setRL02(String rL02) {
		this.rL02 = rL02;
	}

	public String getRL03() {
		return this.rL03;
	}

	public void setRL03(String rL03) {
		this.rL03 = rL03;
	}

	public String getRL04() {
		return this.rL04;
	}

	public void setRL04(String rL04) {
		this.rL04 = rL04;
	}

	public String getRL05() {
		return this.rL05;
	}

	public void setRL05(String rL05) {
		this.rL05 = rL05;
	}

	public String getRL06() {
		return this.rL06;
	}

	public void setRL06(String rL06) {
		this.rL06 = rL06;
	}

	public String getRL07() {
		return this.rL07;
	}

	public void setRL07(String rL07) {
		this.rL07 = rL07;
	}

	public String getRL08() {
		return this.rL08;
	}

	public void setRL08(String rL08) {
		this.rL08 = rL08;
	}

	public String getRL09() {
		return this.rL09;
	}

	public void setRL09(String rL09) {
		this.rL09 = rL09;
	}

	public String getRL101() {
		return this.rL101;
	}

	public void setRL101(String rL101) {
		this.rL101 = rL101;
	}

	public String getRL1010() {
		return this.rL1010;
	}

	public void setRL1010(String rL1010) {
		this.rL1010 = rL1010;
	}

	public String getRL1011() {
		return this.rL1011;
	}

	public void setRL1011(String rL1011) {
		this.rL1011 = rL1011;
	}

	public String getRL1012() {
		return this.rL1012;
	}

	public void setRL1012(String rL1012) {
		this.rL1012 = rL1012;
	}

	public String getRL1013() {
		return this.rL1013;
	}

	public void setRL1013(String rL1013) {
		this.rL1013 = rL1013;
	}

	public String getRL1014() {
		return this.rL1014;
	}

	public void setRL1014(String rL1014) {
		this.rL1014 = rL1014;
	}

	public String getRL1015() {
		return this.rL1015;
	}

	public void setRL1015(String rL1015) {
		this.rL1015 = rL1015;
	}

	public String getRL102() {
		return this.rL102;
	}

	public void setRL102(String rL102) {
		this.rL102 = rL102;
	}

	public String getRL103() {
		return this.rL103;
	}

	public void setRL103(String rL103) {
		this.rL103 = rL103;
	}

	public String getRL104() {
		return this.rL104;
	}

	public void setRL104(String rL104) {
		this.rL104 = rL104;
	}

	public String getRL105() {
		return this.rL105;
	}

	public void setRL105(String rL105) {
		this.rL105 = rL105;
	}

	public String getRL106() {
		return this.rL106;
	}

	public void setRL106(String rL106) {
		this.rL106 = rL106;
	}

	public String getRL107() {
		return this.rL107;
	}

	public void setRL107(String rL107) {
		this.rL107 = rL107;
	}

	public String getRL108() {
		return this.rL108;
	}

	public void setRL108(String rL108) {
		this.rL108 = rL108;
	}

	public String getRL109() {
		return this.rL109;
	}

	public void setRL109(String rL109) {
		this.rL109 = rL109;
	}

	public String getRL11() {
		return this.rL11;
	}

	public void setRL11(String rL11) {
		this.rL11 = rL11;
	}

	public String getRL11_1() {
		return this.rL11_1;
	}

	public void setRL11_1(String rL11_1) {
		this.rL11_1 = rL11_1;
	}

	public String getRL11_10() {
		return this.rL11_10;
	}

	public void setRL11_10(String rL11_10) {
		this.rL11_10 = rL11_10;
	}

	public String getRL11_11() {
		return this.rL11_11;
	}

	public void setRL11_11(String rL11_11) {
		this.rL11_11 = rL11_11;
	}

	public String getRL11_12() {
		return this.rL11_12;
	}

	public void setRL11_12(String rL11_12) {
		this.rL11_12 = rL11_12;
	}

	public String getRL11_13() {
		return this.rL11_13;
	}

	public void setRL11_13(String rL11_13) {
		this.rL11_13 = rL11_13;
	}

	public String getRL11_14() {
		return this.rL11_14;
	}

	public void setRL11_14(String rL11_14) {
		this.rL11_14 = rL11_14;
	}

	public String getRL11_15() {
		return this.rL11_15;
	}

	public void setRL11_15(String rL11_15) {
		this.rL11_15 = rL11_15;
	}

	public String getRL11_2() {
		return this.rL11_2;
	}

	public void setRL11_2(String rL11_2) {
		this.rL11_2 = rL11_2;
	}

	public String getRL11_3() {
		return this.rL11_3;
	}

	public void setRL11_3(String rL11_3) {
		this.rL11_3 = rL11_3;
	}

	public String getRL11_4() {
		return this.rL11_4;
	}

	public void setRL11_4(String rL11_4) {
		this.rL11_4 = rL11_4;
	}

	public String getRL11_5() {
		return this.rL11_5;
	}

	public void setRL11_5(String rL11_5) {
		this.rL11_5 = rL11_5;
	}

	public String getRL11_6() {
		return this.rL11_6;
	}

	public void setRL11_6(String rL11_6) {
		this.rL11_6 = rL11_6;
	}

	public String getRL11_7() {
		return this.rL11_7;
	}

	public void setRL11_7(String rL11_7) {
		this.rL11_7 = rL11_7;
	}

	public String getRL11_8() {
		return this.rL11_8;
	}

	public void setRL11_8(String rL11_8) {
		this.rL11_8 = rL11_8;
	}

	public String getRL11_9() {
		return this.rL11_9;
	}

	public void setRL11_9(String rL11_9) {
		this.rL11_9 = rL11_9;
	}

	public String getRL110() {
		return this.rL110;
	}

	public void setRL110(String rL110) {
		this.rL110 = rL110;
	}

	public String getRL111() {
		return this.rL111;
	}

	public void setRL111(String rL111) {
		this.rL111 = rL111;
	}

	public String getRL112() {
		return this.rL112;
	}

	public void setRL112(String rL112) {
		this.rL112 = rL112;
	}

	public String getRL113() {
		return this.rL113;
	}

	public void setRL113(String rL113) {
		this.rL113 = rL113;
	}

	public String getRL114() {
		return this.rL114;
	}

	public void setRL114(String rL114) {
		this.rL114 = rL114;
	}

	public String getRL115() {
		return this.rL115;
	}

	public void setRL115(String rL115) {
		this.rL115 = rL115;
	}

	public String getRL12() {
		return this.rL12;
	}

	public void setRL12(String rL12) {
		this.rL12 = rL12;
	}

	public String getRL121() {
		return this.rL121;
	}

	public void setRL121(String rL121) {
		this.rL121 = rL121;
	}

	public String getRL1210() {
		return this.rL1210;
	}

	public void setRL1210(String rL1210) {
		this.rL1210 = rL1210;
	}

	public String getRL1211() {
		return this.rL1211;
	}

	public void setRL1211(String rL1211) {
		this.rL1211 = rL1211;
	}

	public String getRL1212() {
		return this.rL1212;
	}

	public void setRL1212(String rL1212) {
		this.rL1212 = rL1212;
	}

	public String getRL1213() {
		return this.rL1213;
	}

	public void setRL1213(String rL1213) {
		this.rL1213 = rL1213;
	}

	public String getRL1214() {
		return this.rL1214;
	}

	public void setRL1214(String rL1214) {
		this.rL1214 = rL1214;
	}

	public String getRL1215() {
		return this.rL1215;
	}

	public void setRL1215(String rL1215) {
		this.rL1215 = rL1215;
	}

	public String getRL122() {
		return this.rL122;
	}

	public void setRL122(String rL122) {
		this.rL122 = rL122;
	}

	public String getRL123() {
		return this.rL123;
	}

	public void setRL123(String rL123) {
		this.rL123 = rL123;
	}

	public String getRL124() {
		return this.rL124;
	}

	public void setRL124(String rL124) {
		this.rL124 = rL124;
	}

	public String getRL125() {
		return this.rL125;
	}

	public void setRL125(String rL125) {
		this.rL125 = rL125;
	}

	public String getRL126() {
		return this.rL126;
	}

	public void setRL126(String rL126) {
		this.rL126 = rL126;
	}

	public String getRL127() {
		return this.rL127;
	}

	public void setRL127(String rL127) {
		this.rL127 = rL127;
	}

	public String getRL128() {
		return this.rL128;
	}

	public void setRL128(String rL128) {
		this.rL128 = rL128;
	}

	public String getRL129() {
		return this.rL129;
	}

	public void setRL129(String rL129) {
		this.rL129 = rL129;
	}

	public String getRL13() {
		return this.rL13;
	}

	public void setRL13(String rL13) {
		this.rL13 = rL13;
	}

	public String getRL131() {
		return this.rL131;
	}

	public void setRL131(String rL131) {
		this.rL131 = rL131;
	}

	public String getRL1310() {
		return this.rL1310;
	}

	public void setRL1310(String rL1310) {
		this.rL1310 = rL1310;
	}

	public String getRL1311() {
		return this.rL1311;
	}

	public void setRL1311(String rL1311) {
		this.rL1311 = rL1311;
	}

	public String getRL1312() {
		return this.rL1312;
	}

	public void setRL1312(String rL1312) {
		this.rL1312 = rL1312;
	}

	public String getRL1313() {
		return this.rL1313;
	}

	public void setRL1313(String rL1313) {
		this.rL1313 = rL1313;
	}

	public String getRL1314() {
		return this.rL1314;
	}

	public void setRL1314(String rL1314) {
		this.rL1314 = rL1314;
	}

	public String getRL1315() {
		return this.rL1315;
	}

	public void setRL1315(String rL1315) {
		this.rL1315 = rL1315;
	}

	public String getRL132() {
		return this.rL132;
	}

	public void setRL132(String rL132) {
		this.rL132 = rL132;
	}

	public String getRL133() {
		return this.rL133;
	}

	public void setRL133(String rL133) {
		this.rL133 = rL133;
	}

	public String getRL134() {
		return this.rL134;
	}

	public void setRL134(String rL134) {
		this.rL134 = rL134;
	}

	public String getRL135() {
		return this.rL135;
	}

	public void setRL135(String rL135) {
		this.rL135 = rL135;
	}

	public String getRL136() {
		return this.rL136;
	}

	public void setRL136(String rL136) {
		this.rL136 = rL136;
	}

	public String getRL137() {
		return this.rL137;
	}

	public void setRL137(String rL137) {
		this.rL137 = rL137;
	}

	public String getRL138() {
		return this.rL138;
	}

	public void setRL138(String rL138) {
		this.rL138 = rL138;
	}

	public String getRL139() {
		return this.rL139;
	}

	public void setRL139(String rL139) {
		this.rL139 = rL139;
	}

	public String getRL14() {
		return this.rL14;
	}

	public void setRL14(String rL14) {
		this.rL14 = rL14;
	}

	public String getRL141() {
		return this.rL141;
	}

	public void setRL141(String rL141) {
		this.rL141 = rL141;
	}

	public String getRL1410() {
		return this.rL1410;
	}

	public void setRL1410(String rL1410) {
		this.rL1410 = rL1410;
	}

	public String getRL1411() {
		return this.rL1411;
	}

	public void setRL1411(String rL1411) {
		this.rL1411 = rL1411;
	}

	public String getRL1412() {
		return this.rL1412;
	}

	public void setRL1412(String rL1412) {
		this.rL1412 = rL1412;
	}

	public String getRL1413() {
		return this.rL1413;
	}

	public void setRL1413(String rL1413) {
		this.rL1413 = rL1413;
	}

	public String getRL1414() {
		return this.rL1414;
	}

	public void setRL1414(String rL1414) {
		this.rL1414 = rL1414;
	}

	public String getRL1415() {
		return this.rL1415;
	}

	public void setRL1415(String rL1415) {
		this.rL1415 = rL1415;
	}

	public String getRL142() {
		return this.rL142;
	}

	public void setRL142(String rL142) {
		this.rL142 = rL142;
	}

	public String getRL143() {
		return this.rL143;
	}

	public void setRL143(String rL143) {
		this.rL143 = rL143;
	}

	public String getRL144() {
		return this.rL144;
	}

	public void setRL144(String rL144) {
		this.rL144 = rL144;
	}

	public String getRL145() {
		return this.rL145;
	}

	public void setRL145(String rL145) {
		this.rL145 = rL145;
	}

	public String getRL146() {
		return this.rL146;
	}

	public void setRL146(String rL146) {
		this.rL146 = rL146;
	}

	public String getRL147() {
		return this.rL147;
	}

	public void setRL147(String rL147) {
		this.rL147 = rL147;
	}

	public String getRL148() {
		return this.rL148;
	}

	public void setRL148(String rL148) {
		this.rL148 = rL148;
	}

	public String getRL149() {
		return this.rL149;
	}

	public void setRL149(String rL149) {
		this.rL149 = rL149;
	}

	public String getRL15() {
		return this.rL15;
	}

	public void setRL15(String rL15) {
		this.rL15 = rL15;
	}

	public String getRL151() {
		return this.rL151;
	}

	public void setRL151(String rL151) {
		this.rL151 = rL151;
	}

	public String getRL1510() {
		return this.rL1510;
	}

	public void setRL1510(String rL1510) {
		this.rL1510 = rL1510;
	}

	public String getRL1511() {
		return this.rL1511;
	}

	public void setRL1511(String rL1511) {
		this.rL1511 = rL1511;
	}

	public String getRL1512() {
		return this.rL1512;
	}

	public void setRL1512(String rL1512) {
		this.rL1512 = rL1512;
	}

	public String getRL1513() {
		return this.rL1513;
	}

	public void setRL1513(String rL1513) {
		this.rL1513 = rL1513;
	}

	public String getRL1514() {
		return this.rL1514;
	}

	public void setRL1514(String rL1514) {
		this.rL1514 = rL1514;
	}

	public String getRL1515() {
		return this.rL1515;
	}

	public void setRL1515(String rL1515) {
		this.rL1515 = rL1515;
	}

	public String getRL152() {
		return this.rL152;
	}

	public void setRL152(String rL152) {
		this.rL152 = rL152;
	}

	public String getRL153() {
		return this.rL153;
	}

	public void setRL153(String rL153) {
		this.rL153 = rL153;
	}

	public String getRL154() {
		return this.rL154;
	}

	public void setRL154(String rL154) {
		this.rL154 = rL154;
	}

	public String getRL155() {
		return this.rL155;
	}

	public void setRL155(String rL155) {
		this.rL155 = rL155;
	}

	public String getRL156() {
		return this.rL156;
	}

	public void setRL156(String rL156) {
		this.rL156 = rL156;
	}

	public String getRL157() {
		return this.rL157;
	}

	public void setRL157(String rL157) {
		this.rL157 = rL157;
	}

	public String getRL158() {
		return this.rL158;
	}

	public void setRL158(String rL158) {
		this.rL158 = rL158;
	}

	public String getRL159() {
		return this.rL159;
	}

	public void setRL159(String rL159) {
		this.rL159 = rL159;
	}

	public String getRL16() {
		return this.rL16;
	}

	public void setRL16(String rL16) {
		this.rL16 = rL16;
	}

	public String getRL161() {
		return this.rL161;
	}

	public void setRL161(String rL161) {
		this.rL161 = rL161;
	}

	public String getRL1610() {
		return this.rL1610;
	}

	public void setRL1610(String rL1610) {
		this.rL1610 = rL1610;
	}

	public String getRL1611() {
		return this.rL1611;
	}

	public void setRL1611(String rL1611) {
		this.rL1611 = rL1611;
	}

	public String getRL1612() {
		return this.rL1612;
	}

	public void setRL1612(String rL1612) {
		this.rL1612 = rL1612;
	}

	public String getRL1613() {
		return this.rL1613;
	}

	public void setRL1613(String rL1613) {
		this.rL1613 = rL1613;
	}

	public String getRL1614() {
		return this.rL1614;
	}

	public void setRL1614(String rL1614) {
		this.rL1614 = rL1614;
	}

	public String getRL1615() {
		return this.rL1615;
	}

	public void setRL1615(String rL1615) {
		this.rL1615 = rL1615;
	}

	public String getRL162() {
		return this.rL162;
	}

	public void setRL162(String rL162) {
		this.rL162 = rL162;
	}

	public String getRL163() {
		return this.rL163;
	}

	public void setRL163(String rL163) {
		this.rL163 = rL163;
	}

	public String getRL164() {
		return this.rL164;
	}

	public void setRL164(String rL164) {
		this.rL164 = rL164;
	}

	public String getRL165() {
		return this.rL165;
	}

	public void setRL165(String rL165) {
		this.rL165 = rL165;
	}

	public String getRL166() {
		return this.rL166;
	}

	public void setRL166(String rL166) {
		this.rL166 = rL166;
	}

	public String getRL167() {
		return this.rL167;
	}

	public void setRL167(String rL167) {
		this.rL167 = rL167;
	}

	public String getRL168() {
		return this.rL168;
	}

	public void setRL168(String rL168) {
		this.rL168 = rL168;
	}

	public String getRL169() {
		return this.rL169;
	}

	public void setRL169(String rL169) {
		this.rL169 = rL169;
	}

	public String getRL17() {
		return this.rL17;
	}

	public void setRL17(String rL17) {
		this.rL17 = rL17;
	}

	public String getRL171() {
		return this.rL171;
	}

	public void setRL171(String rL171) {
		this.rL171 = rL171;
	}

	public String getRL1710() {
		return this.rL1710;
	}

	public void setRL1710(String rL1710) {
		this.rL1710 = rL1710;
	}

	public String getRL1711() {
		return this.rL1711;
	}

	public void setRL1711(String rL1711) {
		this.rL1711 = rL1711;
	}

	public String getRL1712() {
		return this.rL1712;
	}

	public void setRL1712(String rL1712) {
		this.rL1712 = rL1712;
	}

	public String getRL1713() {
		return this.rL1713;
	}

	public void setRL1713(String rL1713) {
		this.rL1713 = rL1713;
	}

	public String getRL1714() {
		return this.rL1714;
	}

	public void setRL1714(String rL1714) {
		this.rL1714 = rL1714;
	}

	public String getRL1715() {
		return this.rL1715;
	}

	public void setRL1715(String rL1715) {
		this.rL1715 = rL1715;
	}

	public String getRL172() {
		return this.rL172;
	}

	public void setRL172(String rL172) {
		this.rL172 = rL172;
	}

	public String getRL173() {
		return this.rL173;
	}

	public void setRL173(String rL173) {
		this.rL173 = rL173;
	}

	public String getRL174() {
		return this.rL174;
	}

	public void setRL174(String rL174) {
		this.rL174 = rL174;
	}

	public String getRL175() {
		return this.rL175;
	}

	public void setRL175(String rL175) {
		this.rL175 = rL175;
	}

	public String getRL176() {
		return this.rL176;
	}

	public void setRL176(String rL176) {
		this.rL176 = rL176;
	}

	public String getRL177() {
		return this.rL177;
	}

	public void setRL177(String rL177) {
		this.rL177 = rL177;
	}

	public String getRL178() {
		return this.rL178;
	}

	public void setRL178(String rL178) {
		this.rL178 = rL178;
	}

	public String getRL179() {
		return this.rL179;
	}

	public void setRL179(String rL179) {
		this.rL179 = rL179;
	}

	public String getRL18() {
		return this.rL18;
	}

	public void setRL18(String rL18) {
		this.rL18 = rL18;
	}

	public String getRL181() {
		return this.rL181;
	}

	public void setRL181(String rL181) {
		this.rL181 = rL181;
	}

	public String getRL1810() {
		return this.rL1810;
	}

	public void setRL1810(String rL1810) {
		this.rL1810 = rL1810;
	}

	public String getRL1811() {
		return this.rL1811;
	}

	public void setRL1811(String rL1811) {
		this.rL1811 = rL1811;
	}

	public String getRL1812() {
		return this.rL1812;
	}

	public void setRL1812(String rL1812) {
		this.rL1812 = rL1812;
	}

	public String getRL1813() {
		return this.rL1813;
	}

	public void setRL1813(String rL1813) {
		this.rL1813 = rL1813;
	}

	public String getRL1814() {
		return this.rL1814;
	}

	public void setRL1814(String rL1814) {
		this.rL1814 = rL1814;
	}

	public String getRL1815() {
		return this.rL1815;
	}

	public void setRL1815(String rL1815) {
		this.rL1815 = rL1815;
	}

	public String getRL182() {
		return this.rL182;
	}

	public void setRL182(String rL182) {
		this.rL182 = rL182;
	}

	public String getRL183() {
		return this.rL183;
	}

	public void setRL183(String rL183) {
		this.rL183 = rL183;
	}

	public String getRL184() {
		return this.rL184;
	}

	public void setRL184(String rL184) {
		this.rL184 = rL184;
	}

	public String getRL185() {
		return this.rL185;
	}

	public void setRL185(String rL185) {
		this.rL185 = rL185;
	}

	public String getRL186() {
		return this.rL186;
	}

	public void setRL186(String rL186) {
		this.rL186 = rL186;
	}

	public String getRL187() {
		return this.rL187;
	}

	public void setRL187(String rL187) {
		this.rL187 = rL187;
	}

	public String getRL188() {
		return this.rL188;
	}

	public void setRL188(String rL188) {
		this.rL188 = rL188;
	}

	public String getRL189() {
		return this.rL189;
	}

	public void setRL189(String rL189) {
		this.rL189 = rL189;
	}

	public String getRL19() {
		return this.rL19;
	}

	public void setRL19(String rL19) {
		this.rL19 = rL19;
	}

	public String getRL191() {
		return this.rL191;
	}

	public void setRL191(String rL191) {
		this.rL191 = rL191;
	}

	public String getRL1910() {
		return this.rL1910;
	}

	public void setRL1910(String rL1910) {
		this.rL1910 = rL1910;
	}

	public String getRL1911() {
		return this.rL1911;
	}

	public void setRL1911(String rL1911) {
		this.rL1911 = rL1911;
	}

	public String getRL1912() {
		return this.rL1912;
	}

	public void setRL1912(String rL1912) {
		this.rL1912 = rL1912;
	}

	public String getRL1913() {
		return this.rL1913;
	}

	public void setRL1913(String rL1913) {
		this.rL1913 = rL1913;
	}

	public String getRL1914() {
		return this.rL1914;
	}

	public void setRL1914(String rL1914) {
		this.rL1914 = rL1914;
	}

	public String getRL1915() {
		return this.rL1915;
	}

	public void setRL1915(String rL1915) {
		this.rL1915 = rL1915;
	}

	public String getRL192() {
		return this.rL192;
	}

	public void setRL192(String rL192) {
		this.rL192 = rL192;
	}

	public String getRL193() {
		return this.rL193;
	}

	public void setRL193(String rL193) {
		this.rL193 = rL193;
	}

	public String getRL194() {
		return this.rL194;
	}

	public void setRL194(String rL194) {
		this.rL194 = rL194;
	}

	public String getRL195() {
		return this.rL195;
	}

	public void setRL195(String rL195) {
		this.rL195 = rL195;
	}

	public String getRL196() {
		return this.rL196;
	}

	public void setRL196(String rL196) {
		this.rL196 = rL196;
	}

	public String getRL197() {
		return this.rL197;
	}

	public void setRL197(String rL197) {
		this.rL197 = rL197;
	}

	public String getRL198() {
		return this.rL198;
	}

	public void setRL198(String rL198) {
		this.rL198 = rL198;
	}

	public String getRL199() {
		return this.rL199;
	}

	public void setRL199(String rL199) {
		this.rL199 = rL199;
	}

	public String getRL201() {
		return this.rL201;
	}

	public void setRL201(String rL201) {
		this.rL201 = rL201;
	}

	public String getRL2010() {
		return this.rL2010;
	}

	public void setRL2010(String rL2010) {
		this.rL2010 = rL2010;
	}

	public String getRL2011() {
		return this.rL2011;
	}

	public void setRL2011(String rL2011) {
		this.rL2011 = rL2011;
	}

	public String getRL2012() {
		return this.rL2012;
	}

	public void setRL2012(String rL2012) {
		this.rL2012 = rL2012;
	}

	public String getRL2013() {
		return this.rL2013;
	}

	public void setRL2013(String rL2013) {
		this.rL2013 = rL2013;
	}

	public String getRL2014() {
		return this.rL2014;
	}

	public void setRL2014(String rL2014) {
		this.rL2014 = rL2014;
	}

	public String getRL2015() {
		return this.rL2015;
	}

	public void setRL2015(String rL2015) {
		this.rL2015 = rL2015;
	}

	public String getRL202() {
		return this.rL202;
	}

	public void setRL202(String rL202) {
		this.rL202 = rL202;
	}

	public String getRL203() {
		return this.rL203;
	}

	public void setRL203(String rL203) {
		this.rL203 = rL203;
	}

	public String getRL204() {
		return this.rL204;
	}

	public void setRL204(String rL204) {
		this.rL204 = rL204;
	}

	public String getRL205() {
		return this.rL205;
	}

	public void setRL205(String rL205) {
		this.rL205 = rL205;
	}

	public String getRL206() {
		return this.rL206;
	}

	public void setRL206(String rL206) {
		this.rL206 = rL206;
	}

	public String getRL207() {
		return this.rL207;
	}

	public void setRL207(String rL207) {
		this.rL207 = rL207;
	}

	public String getRL208() {
		return this.rL208;
	}

	public void setRL208(String rL208) {
		this.rL208 = rL208;
	}

	public String getRL209() {
		return this.rL209;
	}

	public void setRL209(String rL209) {
		this.rL209 = rL209;
	}

	public String getRL21() {
		return this.rL21;
	}

	public void setRL21(String rL21) {
		this.rL21 = rL21;
	}

	public String getRL21_1() {
		return this.rL21_1;
	}

	public void setRL21_1(String rL21_1) {
		this.rL21_1 = rL21_1;
	}

	public String getRL21_10() {
		return this.rL21_10;
	}

	public void setRL2110(String rL21_10) {
		this.rL21_10 = rL21_10;
	}

	public String getRL21_11() {
		return this.rL21_11;
	}

	public void setRL2111(String rL21_11) {
		this.rL21_11 = rL21_11;
	}

	public String getRL21_12() {
		return this.rL21_12;
	}

	public void setRL21_12(String rL21_12) {
		this.rL21_12 = rL21_12;
	}

	public String getRL21_13() {
		return this.rL21_13;
	}

	public void setRL21_13(String rL21_13) {
		this.rL21_13 = rL21_13;
	}

	public String getRL21_14() {
		return this.rL21_14;
	}

	public void setRL2114(String rL21_14) {
		this.rL21_14 = rL21_14;
	}

	public String getRL21_15() {
		return this.rL21_15;
	}

	public void setRL21_15(String rL21_15) {
		this.rL21_15 = rL21_15;
	}

	public String getRL21_2() {
		return this.rL21_2;
	}

	public void setRL21_2(String rL21_2) {
		this.rL21_2 = rL21_2;
	}

	public String getRL21_3() {
		return this.rL21_3;
	}

	public void setRL21_3(String rL21_3) {
		this.rL21_3 = rL21_3;
	}

	public String getRL21_4() {
		return this.rL21_4;
	}

	public void setRL21_4(String rL21_4) {
		this.rL21_4 = rL21_4;
	}

	public String getRL21_5() {
		return this.rL21_5;
	}

	public void setRL21_5(String rL21_5) {
		this.rL21_5 = rL21_5;
	}

	public String getRL21_6() {
		return this.rL21_6;
	}

	public void setRL21_6(String rL21_6) {
		this.rL21_6 = rL21_6;
	}

	public String getRL21_7() {
		return this.rL21_7;
	}

	public void setRL21_7(String rL21_7) {
		this.rL21_7 = rL21_7;
	}

	public String getRL21_8() {
		return this.rL21_8;
	}

	public void setRL21_8(String rL21_8) {
		this.rL21_8 = rL21_8;
	}

	public String getRL21_9() {
		return this.rL21_9;
	}

	public void setRL21_9(String rL21_9) {
		this.rL21_9 = rL21_9;
	}

	public String getRL210() {
		return this.rL210;
	}

	public void setRL210(String rL210) {
		this.rL210 = rL210;
	}

	public String getRL211() {
		return this.rL211;
	}

	public void setRL211(String rL211) {
		this.rL211 = rL211;
	}

	public String getRL212() {
		return this.rL212;
	}

	public void setRL212(String rL212) {
		this.rL212 = rL212;
	}

	public String getRL213() {
		return this.rL213;
	}

	public void setRL213(String rL213) {
		this.rL213 = rL213;
	}

	public String getRL214() {
		return this.rL214;
	}

	public void setRL214(String rL214) {
		this.rL214 = rL214;
	}

	public String getRL215() {
		return this.rL215;
	}

	public void setRL215(String rL215) {
		this.rL215 = rL215;
	}

	public String getRL22() {
		return this.rL22;
	}

	public void setRL22(String rL22) {
		this.rL22 = rL22;
	}

	public String getRL221() {
		return this.rL221;
	}

	public void setRL221(String rL221) {
		this.rL221 = rL221;
	}

	public String getRL2210() {
		return this.rL2210;
	}

	public void setRL2210(String rL2210) {
		this.rL2210 = rL2210;
	}

	public String getRL2211() {
		return this.rL2211;
	}

	public void setRL2211(String rL2211) {
		this.rL2211 = rL2211;
	}

	public String getRL2212() {
		return this.rL2212;
	}

	public void setRL2212(String rL2212) {
		this.rL2212 = rL2212;
	}

	public String getRL2213() {
		return this.rL2213;
	}

	public void setRL2213(String rL2213) {
		this.rL2213 = rL2213;
	}

	public String getRL2214() {
		return this.rL2214;
	}

	public void setRL2214(String rL2214) {
		this.rL2214 = rL2214;
	}

	public String getRL2215() {
		return this.rL2215;
	}

	public void setRL2215(String rL2215) {
		this.rL2215 = rL2215;
	}

	public String getRL222() {
		return this.rL222;
	}

	public void setRL222(String rL222) {
		this.rL222 = rL222;
	}

	public String getRL223() {
		return this.rL223;
	}

	public void setRL223(String rL223) {
		this.rL223 = rL223;
	}

	public String getRL224() {
		return this.rL224;
	}

	public void setRL224(String rL224) {
		this.rL224 = rL224;
	}

	public String getRL225() {
		return this.rL225;
	}

	public void setRL225(String rL225) {
		this.rL225 = rL225;
	}

	public String getRL226() {
		return this.rL226;
	}

	public void setRL226(String rL226) {
		this.rL226 = rL226;
	}

	public String getRL227() {
		return this.rL227;
	}

	public void setRL227(String rL227) {
		this.rL227 = rL227;
	}

	public String getRL228() {
		return this.rL228;
	}

	public void setRL228(String rL228) {
		this.rL228 = rL228;
	}

	public String getRL229() {
		return this.rL229;
	}

	public void setRL229(String rL229) {
		this.rL229 = rL229;
	}

	public String getRL23() {
		return this.rL23;
	}

	public void setRL23(String rL23) {
		this.rL23 = rL23;
	}

	public String getRL231() {
		return this.rL231;
	}

	public void setRL231(String rL231) {
		this.rL231 = rL231;
	}

	public String getRL2310() {
		return this.rL2310;
	}

	public void setRL2310(String rL2310) {
		this.rL2310 = rL2310;
	}

	public String getRL2311() {
		return this.rL2311;
	}

	public void setRL2311(String rL2311) {
		this.rL2311 = rL2311;
	}

	public String getRL2312() {
		return this.rL2312;
	}

	public void setRL2312(String rL2312) {
		this.rL2312 = rL2312;
	}

	public String getRL2313() {
		return this.rL2313;
	}

	public void setRL2313(String rL2313) {
		this.rL2313 = rL2313;
	}

	public String getRL2314() {
		return this.rL2314;
	}

	public void setRL2314(String rL2314) {
		this.rL2314 = rL2314;
	}

	public String getRL2315() {
		return this.rL2315;
	}

	public void setRL2315(String rL2315) {
		this.rL2315 = rL2315;
	}

	public String getRL232() {
		return this.rL232;
	}

	public void setRL232(String rL232) {
		this.rL232 = rL232;
	}

	public String getRL233() {
		return this.rL233;
	}

	public void setRL233(String rL233) {
		this.rL233 = rL233;
	}

	public String getRL234() {
		return this.rL234;
	}

	public void setRL234(String rL234) {
		this.rL234 = rL234;
	}

	public String getRL235() {
		return this.rL235;
	}

	public void setRL235(String rL235) {
		this.rL235 = rL235;
	}

	public String getRL236() {
		return this.rL236;
	}

	public void setRL236(String rL236) {
		this.rL236 = rL236;
	}

	public String getRL237() {
		return this.rL237;
	}

	public void setRL237(String rL237) {
		this.rL237 = rL237;
	}

	public String getRL238() {
		return this.rL238;
	}

	public void setRL238(String rL238) {
		this.rL238 = rL238;
	}

	public String getRL239() {
		return this.rL239;
	}

	public void setRL239(String rL239) {
		this.rL239 = rL239;
	}

	public String getRL24() {
		return this.rL24;
	}

	public void setRL24(String rL24) {
		this.rL24 = rL24;
	}

	public String getRL241() {
		return this.rL241;
	}

	public void setRL241(String rL241) {
		this.rL241 = rL241;
	}

	public String getRL2410() {
		return this.rL2410;
	}

	public void setRL2410(String rL2410) {
		this.rL2410 = rL2410;
	}

	public String getRL2411() {
		return this.rL2411;
	}

	public void setRL2411(String rL2411) {
		this.rL2411 = rL2411;
	}

	public String getRL2412() {
		return this.rL2412;
	}

	public void setRL2412(String rL2412) {
		this.rL2412 = rL2412;
	}

	public String getRL2413() {
		return this.rL2413;
	}

	public void setRL2413(String rL2413) {
		this.rL2413 = rL2413;
	}

	public String getRL2414() {
		return this.rL2414;
	}

	public void setRL2414(String rL2414) {
		this.rL2414 = rL2414;
	}

	public String getRL2415() {
		return this.rL2415;
	}

	public void setRL2415(String rL2415) {
		this.rL2415 = rL2415;
	}

	public String getRL242() {
		return this.rL242;
	}

	public void setRL242(String rL242) {
		this.rL242 = rL242;
	}

	public String getRL243() {
		return this.rL243;
	}

	public void setRL243(String rL243) {
		this.rL243 = rL243;
	}

	public String getRL244() {
		return this.rL244;
	}

	public void setRL244(String rL244) {
		this.rL244 = rL244;
	}

	public String getRL245() {
		return this.rL245;
	}

	public void setRL245(String rL245) {
		this.rL245 = rL245;
	}

	public String getRL246() {
		return this.rL246;
	}

	public void setRL246(String rL246) {
		this.rL246 = rL246;
	}

	public String getRL247() {
		return this.rL247;
	}

	public void setRL247(String rL247) {
		this.rL247 = rL247;
	}

	public String getRL248() {
		return this.rL248;
	}

	public void setRL248(String rL248) {
		this.rL248 = rL248;
	}

	public String getRL249() {
		return this.rL249;
	}

	public void setRL249(String rL249) {
		this.rL249 = rL249;
	}

	public String getRL25() {
		return this.rL25;
	}

	public void setRL25(String rL25) {
		this.rL25 = rL25;
	}

	public String getRL251() {
		return this.rL251;
	}

	public void setRL251(String rL251) {
		this.rL251 = rL251;
	}

	public String getRL2510() {
		return this.rL2510;
	}

	public void setRL2510(String rL2510) {
		this.rL2510 = rL2510;
	}

	public String getRL2511() {
		return this.rL2511;
	}

	public void setRL2511(String rL2511) {
		this.rL2511 = rL2511;
	}

	public String getRL2512() {
		return this.rL2512;
	}

	public void setRL2512(String rL2512) {
		this.rL2512 = rL2512;
	}

	public String getRL2513() {
		return this.rL2513;
	}

	public void setRL2513(String rL2513) {
		this.rL2513 = rL2513;
	}

	public String getRL2514() {
		return this.rL2514;
	}

	public void setRL2514(String rL2514) {
		this.rL2514 = rL2514;
	}

	public String getRL2515() {
		return this.rL2515;
	}

	public void setRL2515(String rL2515) {
		this.rL2515 = rL2515;
	}

	public String getRL252() {
		return this.rL252;
	}

	public void setRL252(String rL252) {
		this.rL252 = rL252;
	}

	public String getRL253() {
		return this.rL253;
	}

	public void setRL253(String rL253) {
		this.rL253 = rL253;
	}

	public String getRL254() {
		return this.rL254;
	}

	public void setRL254(String rL254) {
		this.rL254 = rL254;
	}

	public String getRL255() {
		return this.rL255;
	}

	public void setRL255(String rL255) {
		this.rL255 = rL255;
	}

	public String getRL256() {
		return this.rL256;
	}

	public void setRL256(String rL256) {
		this.rL256 = rL256;
	}

	public String getRL257() {
		return this.rL257;
	}

	public void setRL257(String rL257) {
		this.rL257 = rL257;
	}

	public String getRL258() {
		return this.rL258;
	}

	public void setRL258(String rL258) {
		this.rL258 = rL258;
	}

	public String getRL259() {
		return this.rL259;
	}

	public void setRL259(String rL259) {
		this.rL259 = rL259;
	}

	public String getRL26() {
		return this.rL26;
	}

	public void setRL26(String rL26) {
		this.rL26 = rL26;
	}

	public String getRL261() {
		return this.rL261;
	}

	public void setRL261(String rL261) {
		this.rL261 = rL261;
	}

	public String getRL2610() {
		return this.rL2610;
	}

	public void setRL2610(String rL2610) {
		this.rL2610 = rL2610;
	}

	public String getRL2611() {
		return this.rL2611;
	}

	public void setRL2611(String rL2611) {
		this.rL2611 = rL2611;
	}

	public String getRL2612() {
		return this.rL2612;
	}

	public void setRL2612(String rL2612) {
		this.rL2612 = rL2612;
	}

	public String getRL2613() {
		return this.rL2613;
	}

	public void setRL2613(String rL2613) {
		this.rL2613 = rL2613;
	}

	public String getRL2614() {
		return this.rL2614;
	}

	public void setRL2614(String rL2614) {
		this.rL2614 = rL2614;
	}

	public String getRL2615() {
		return this.rL2615;
	}

	public void setRL2615(String rL2615) {
		this.rL2615 = rL2615;
	}

	public String getRL262() {
		return this.rL262;
	}

	public void setRL262(String rL262) {
		this.rL262 = rL262;
	}

	public String getRL263() {
		return this.rL263;
	}

	public void setRL263(String rL263) {
		this.rL263 = rL263;
	}

	public String getRL264() {
		return this.rL264;
	}

	public void setRL264(String rL264) {
		this.rL264 = rL264;
	}

	public String getRL265() {
		return this.rL265;
	}

	public void setRL265(String rL265) {
		this.rL265 = rL265;
	}

	public String getRL266() {
		return this.rL266;
	}

	public void setRL266(String rL266) {
		this.rL266 = rL266;
	}

	public String getRL267() {
		return this.rL267;
	}

	public void setRL267(String rL267) {
		this.rL267 = rL267;
	}

	public String getRL268() {
		return this.rL268;
	}

	public void setRL268(String rL268) {
		this.rL268 = rL268;
	}

	public String getRL269() {
		return this.rL269;
	}

	public void setRL269(String rL269) {
		this.rL269 = rL269;
	}

	public String getRL27() {
		return this.rL27;
	}

	public void setRL27(String rL27) {
		this.rL27 = rL27;
	}

	public String getRL271() {
		return this.rL271;
	}

	public void setRL271(String rL271) {
		this.rL271 = rL271;
	}

	public String getRL2710() {
		return this.rL2710;
	}

	public void setRL2710(String rL2710) {
		this.rL2710 = rL2710;
	}

	public String getRL2711() {
		return this.rL2711;
	}

	public void setRL2711(String rL2711) {
		this.rL2711 = rL2711;
	}

	public String getRL2712() {
		return this.rL2712;
	}

	public void setRL2712(String rL2712) {
		this.rL2712 = rL2712;
	}

	public String getRL2713() {
		return this.rL2713;
	}

	public void setRL2713(String rL2713) {
		this.rL2713 = rL2713;
	}

	public String getRL2714() {
		return this.rL2714;
	}

	public void setRL2714(String rL2714) {
		this.rL2714 = rL2714;
	}

	public String getRL2715() {
		return this.rL2715;
	}

	public void setRL2715(String rL2715) {
		this.rL2715 = rL2715;
	}

	public String getRL272() {
		return this.rL272;
	}

	public void setRL272(String rL272) {
		this.rL272 = rL272;
	}

	public String getRL273() {
		return this.rL273;
	}

	public void setRL273(String rL273) {
		this.rL273 = rL273;
	}

	public String getRL274() {
		return this.rL274;
	}

	public void setRL274(String rL274) {
		this.rL274 = rL274;
	}

	public String getRL275() {
		return this.rL275;
	}

	public void setRL275(String rL275) {
		this.rL275 = rL275;
	}

	public String getRL276() {
		return this.rL276;
	}

	public void setRL276(String rL276) {
		this.rL276 = rL276;
	}

	public String getRL277() {
		return this.rL277;
	}

	public void setRL277(String rL277) {
		this.rL277 = rL277;
	}

	public String getRL278() {
		return this.rL278;
	}

	public void setRL278(String rL278) {
		this.rL278 = rL278;
	}

	public String getRL279() {
		return this.rL279;
	}

	public void setRL279(String rL279) {
		this.rL279 = rL279;
	}

	public String getRL28() {
		return this.rL28;
	}

	public void setRL28(String rL28) {
		this.rL28 = rL28;
	}

	public String getRL281() {
		return this.rL281;
	}

	public void setRL281(String rL281) {
		this.rL281 = rL281;
	}

	public String getRL2810() {
		return this.rL2810;
	}

	public void setRL2810(String rL2810) {
		this.rL2810 = rL2810;
	}

	public String getRL2811() {
		return this.rL2811;
	}

	public void setRL2811(String rL2811) {
		this.rL2811 = rL2811;
	}

	public String getRL2812() {
		return this.rL2812;
	}

	public void setRL2812(String rL2812) {
		this.rL2812 = rL2812;
	}

	public String getRL2813() {
		return this.rL2813;
	}

	public void setRL2813(String rL2813) {
		this.rL2813 = rL2813;
	}

	public String getRL2814() {
		return this.rL2814;
	}

	public void setRL2814(String rL2814) {
		this.rL2814 = rL2814;
	}

	public String getRL2815() {
		return this.rL2815;
	}

	public void setRL2815(String rL2815) {
		this.rL2815 = rL2815;
	}

	public String getRL282() {
		return this.rL282;
	}

	public void setRL282(String rL282) {
		this.rL282 = rL282;
	}

	public String getRL283() {
		return this.rL283;
	}

	public void setRL283(String rL283) {
		this.rL283 = rL283;
	}

	public String getRL284() {
		return this.rL284;
	}

	public void setRL284(String rL284) {
		this.rL284 = rL284;
	}

	public String getRL285() {
		return this.rL285;
	}

	public void setRL285(String rL285) {
		this.rL285 = rL285;
	}

	public String getRL286() {
		return this.rL286;
	}

	public void setRL286(String rL286) {
		this.rL286 = rL286;
	}

	public String getRL287() {
		return this.rL287;
	}

	public void setRL287(String rL287) {
		this.rL287 = rL287;
	}

	public String getRL288() {
		return this.rL288;
	}

	public void setRL288(String rL288) {
		this.rL288 = rL288;
	}

	public String getRL289() {
		return this.rL289;
	}

	public void setRL289(String rL289) {
		this.rL289 = rL289;
	}

	public String getRL29() {
		return this.rL29;
	}

	public void setRL29(String rL29) {
		this.rL29 = rL29;
	}

	public String getRL291() {
		return this.rL291;
	}

	public void setRL291(String rL291) {
		this.rL291 = rL291;
	}

	public String getRL2910() {
		return this.rL2910;
	}

	public void setRL2910(String rL2910) {
		this.rL2910 = rL2910;
	}

	public String getRL2911() {
		return this.rL2911;
	}

	public void setRL2911(String rL2911) {
		this.rL2911 = rL2911;
	}

	public String getRL2912() {
		return this.rL2912;
	}

	public void setRL2912(String rL2912) {
		this.rL2912 = rL2912;
	}

	public String getRL2913() {
		return this.rL2913;
	}

	public void setRL2913(String rL2913) {
		this.rL2913 = rL2913;
	}

	public String getRL2914() {
		return this.rL2914;
	}

	public void setRL2914(String rL2914) {
		this.rL2914 = rL2914;
	}

	public String getRL2915() {
		return this.rL2915;
	}

	public void setRL2915(String rL2915) {
		this.rL2915 = rL2915;
	}

	public String getRL292() {
		return this.rL292;
	}

	public void setRL292(String rL292) {
		this.rL292 = rL292;
	}

	public String getRL293() {
		return this.rL293;
	}

	public void setRL293(String rL293) {
		this.rL293 = rL293;
	}

	public String getRL294() {
		return this.rL294;
	}

	public void setRL294(String rL294) {
		this.rL294 = rL294;
	}

	public String getRL295() {
		return this.rL295;
	}

	public void setRL295(String rL295) {
		this.rL295 = rL295;
	}

	public String getRL296() {
		return this.rL296;
	}

	public void setRL296(String rL296) {
		this.rL296 = rL296;
	}

	public String getRL297() {
		return this.rL297;
	}

	public void setRL297(String rL297) {
		this.rL297 = rL297;
	}

	public String getRL298() {
		return this.rL298;
	}

	public void setRL298(String rL298) {
		this.rL298 = rL298;
	}

	public String getRL299() {
		return this.rL299;
	}

	public void setRL299(String rL299) {
		this.rL299 = rL299;
	}

	public String getRL301() {
		return this.rL301;
	}

	public void setRL301(String rL301) {
		this.rL301 = rL301;
	}

	public String getRL3010() {
		return this.rL3010;
	}

	public void setRL3010(String rL3010) {
		this.rL3010 = rL3010;
	}

	public String getRL3011() {
		return this.rL3011;
	}

	public void setRL3011(String rL3011) {
		this.rL3011 = rL3011;
	}

	public String getRL3012() {
		return this.rL3012;
	}

	public void setRL3012(String rL3012) {
		this.rL3012 = rL3012;
	}

	public String getRL3013() {
		return this.rL3013;
	}

	public void setRL3013(String rL3013) {
		this.rL3013 = rL3013;
	}

	public String getRL3014() {
		return this.rL3014;
	}

	public void setRL3014(String rL3014) {
		this.rL3014 = rL3014;
	}

	public String getRL3015() {
		return this.rL3015;
	}

	public void setRL3015(String rL3015) {
		this.rL3015 = rL3015;
	}

	public String getRL302() {
		return this.rL302;
	}

	public void setRL302(String rL302) {
		this.rL302 = rL302;
	}

	public String getRL303() {
		return this.rL303;
	}

	public void setRL303(String rL303) {
		this.rL303 = rL303;
	}

	public String getRL304() {
		return this.rL304;
	}

	public void setRL304(String rL304) {
		this.rL304 = rL304;
	}

	public String getRL305() {
		return this.rL305;
	}

	public void setRL305(String rL305) {
		this.rL305 = rL305;
	}

	public String getRL306() {
		return this.rL306;
	}

	public void setRL306(String rL306) {
		this.rL306 = rL306;
	}

	public String getRL307() {
		return this.rL307;
	}

	public void setRL307(String rL307) {
		this.rL307 = rL307;
	}

	public String getRL308() {
		return this.rL308;
	}

	public void setRL308(String rL308) {
		this.rL308 = rL308;
	}

	public String getRL309() {
		return this.rL309;
	}

	public void setRL309(String rL309) {
		this.rL309 = rL309;
	}

	public String getRL31() {
		return this.rL31;
	}

	public void setRL31(String rL31) {
		this.rL31 = rL31;
	}

	public String getRL31_1() {
		return this.rL31_1;
	}

	public void setRL31_1(String rL31_1) {
		this.rL31_1 = rL31_1;
	}

	public String getRL31_10() {
		return this.rL31_10;
	}

	public void setRL31_10(String rL31_10) {
		this.rL31_10 = rL31_10;
	}

	public String getRL31_11() {
		return this.rL31_11;
	}

	public void setRL31_11(String rL31_11) {
		this.rL31_11 = rL31_11;
	}

	public String getRL31_12() {
		return this.rL31_12;
	}

	public void setRL31_12(String rL31_12) {
		this.rL31_12 = rL31_12;
	}

	public String getRL31_13() {
		return this.rL31_13;
	}

	public void setRL31_13(String rL31_13) {
		this.rL31_13 = rL31_13;
	}

	public String getRL31_14() {
		return this.rL31_14;
	}

	public void setRL31_14(String rL31_14) {
		this.rL31_14 = rL31_14;
	}

	public String getRL31_15() {
		return this.rL31_15;
	}

	public void setRL3115(String rL31_15) {
		this.rL31_15 = rL31_15;
	}

	public String getRL31_2() {
		return this.rL31_2;
	}

	public void setRL31_2(String rL31_2) {
		this.rL31_2 = rL31_2;
	}

	public String getRL31_3() {
		return this.rL31_3;
	}

	public void setRL31_3(String rL31_3) {
		this.rL31_3 = rL31_3;
	}

	public String getRL31_4() {
		return this.rL31_4;
	}

	public void setRL31_4(String rL31_4) {
		this.rL31_4 = rL31_4;
	}

	public String getRL31_5() {
		return this.rL31_5;
	}

	public void setRL31_5(String rL31_5) {
		this.rL31_5 = rL31_5;
	}

	public String getRL31_6() {
		return this.rL31_6;
	}

	public void setRL31_6(String rL31_6) {
		this.rL31_6 = rL31_6;
	}

	public String getRL31_7() {
		return this.rL31_7;
	}

	public void setRL31_7(String rL31_7) {
		this.rL31_7 = rL31_7;
	}

	public String getRL31_8() {
		return this.rL31_8;
	}

	public void setRL31_8(String rL31_8) {
		this.rL31_8 = rL31_8;
	}

	public String getRL31_9() {
		return this.rL31_9;
	}

	public void setRL31_9(String rL31_9) {
		this.rL31_9 = rL31_9;
	}

	public String getRL310() {
		return this.rL310;
	}

	public void setRL310(String rL310) {
		this.rL310 = rL310;
	}

	public String getRL311() {
		return this.rL311;
	}

	public void setRL311(String rL311) {
		this.rL311 = rL311;
	}

	public String getRL312() {
		return this.rL312;
	}

	public void setRL312(String rL312) {
		this.rL312 = rL312;
	}

	public String getRL313() {
		return this.rL313;
	}

	public void setRL313(String rL313) {
		this.rL313 = rL313;
	}

	public String getRL314() {
		return this.rL314;
	}

	public void setRL314(String rL314) {
		this.rL314 = rL314;
	}

	public String getRL315() {
		return this.rL315;
	}

	public void setRL315(String rL315) {
		this.rL315 = rL315;
	}

	public String getRL32() {
		return this.rL32;
	}

	public void setRL32(String rL32) {
		this.rL32 = rL32;
	}

	public String getRL321() {
		return this.rL321;
	}

	public void setRL321(String rL321) {
		this.rL321 = rL321;
	}

	public String getRL3210() {
		return this.rL3210;
	}

	public void setRL3210(String rL3210) {
		this.rL3210 = rL3210;
	}

	public String getRL3211() {
		return this.rL3211;
	}

	public void setRL3211(String rL3211) {
		this.rL3211 = rL3211;
	}

	public String getRL3212() {
		return this.rL3212;
	}

	public void setRL3212(String rL3212) {
		this.rL3212 = rL3212;
	}

	public String getRL3213() {
		return this.rL3213;
	}

	public void setRL3213(String rL3213) {
		this.rL3213 = rL3213;
	}

	public String getRL3214() {
		return this.rL3214;
	}

	public void setRL3214(String rL3214) {
		this.rL3214 = rL3214;
	}

	public String getRL3215() {
		return this.rL3215;
	}

	public void setRL3215(String rL3215) {
		this.rL3215 = rL3215;
	}

	public String getRL322() {
		return this.rL322;
	}

	public void setRL322(String rL322) {
		this.rL322 = rL322;
	}

	public String getRL323() {
		return this.rL323;
	}

	public void setRL323(String rL323) {
		this.rL323 = rL323;
	}

	public String getRL324() {
		return this.rL324;
	}

	public void setRL324(String rL324) {
		this.rL324 = rL324;
	}

	public String getRL325() {
		return this.rL325;
	}

	public void setRL325(String rL325) {
		this.rL325 = rL325;
	}

	public String getRL326() {
		return this.rL326;
	}

	public void setRL326(String rL326) {
		this.rL326 = rL326;
	}

	public String getRL327() {
		return this.rL327;
	}

	public void setRL327(String rL327) {
		this.rL327 = rL327;
	}

	public String getRL328() {
		return this.rL328;
	}

	public void setRL328(String rL328) {
		this.rL328 = rL328;
	}

	public String getRL329() {
		return this.rL329;
	}

	public void setRL329(String rL329) {
		this.rL329 = rL329;
	}

	public String getRL33() {
		return this.rL33;
	}

	public void setRL33(String rL33) {
		this.rL33 = rL33;
	}

	public String getRL331() {
		return this.rL331;
	}

	public void setRL331(String rL331) {
		this.rL331 = rL331;
	}

	public String getRL3310() {
		return this.rL3310;
	}

	public void setRL3310(String rL3310) {
		this.rL3310 = rL3310;
	}

	public String getRL3311() {
		return this.rL3311;
	}

	public void setRL3311(String rL3311) {
		this.rL3311 = rL3311;
	}

	public String getRL3312() {
		return this.rL3312;
	}

	public void setRL3312(String rL3312) {
		this.rL3312 = rL3312;
	}

	public String getRL3313() {
		return this.rL3313;
	}

	public void setRL3313(String rL3313) {
		this.rL3313 = rL3313;
	}

	public String getRL3314() {
		return this.rL3314;
	}

	public void setRL3314(String rL3314) {
		this.rL3314 = rL3314;
	}

	public String getRL3315() {
		return this.rL3315;
	}

	public void setRL3315(String rL3315) {
		this.rL3315 = rL3315;
	}

	public String getRL332() {
		return this.rL332;
	}

	public void setRL332(String rL332) {
		this.rL332 = rL332;
	}

	public String getRL333() {
		return this.rL333;
	}

	public void setRL333(String rL333) {
		this.rL333 = rL333;
	}

	public String getRL334() {
		return this.rL334;
	}

	public void setRL334(String rL334) {
		this.rL334 = rL334;
	}

	public String getRL335() {
		return this.rL335;
	}

	public void setRL335(String rL335) {
		this.rL335 = rL335;
	}

	public String getRL336() {
		return this.rL336;
	}

	public void setRL336(String rL336) {
		this.rL336 = rL336;
	}

	public String getRL337() {
		return this.rL337;
	}

	public void setRL337(String rL337) {
		this.rL337 = rL337;
	}

	public String getRL338() {
		return this.rL338;
	}

	public void setRL338(String rL338) {
		this.rL338 = rL338;
	}

	public String getRL339() {
		return this.rL339;
	}

	public void setRL339(String rL339) {
		this.rL339 = rL339;
	}

	public String getRL34() {
		return this.rL34;
	}

	public void setRL34(String rL34) {
		this.rL34 = rL34;
	}

	public String getRL341() {
		return this.rL341;
	}

	public void setRL341(String rL341) {
		this.rL341 = rL341;
	}

	public String getRL3410() {
		return this.rL3410;
	}

	public void setRL3410(String rL3410) {
		this.rL3410 = rL3410;
	}

	public String getRL3411() {
		return this.rL3411;
	}

	public void setRL3411(String rL3411) {
		this.rL3411 = rL3411;
	}

	public String getRL3412() {
		return this.rL3412;
	}

	public void setRL3412(String rL3412) {
		this.rL3412 = rL3412;
	}

	public String getRL3413() {
		return this.rL3413;
	}

	public void setRL3413(String rL3413) {
		this.rL3413 = rL3413;
	}

	public String getRL3414() {
		return this.rL3414;
	}

	public void setRL3414(String rL3414) {
		this.rL3414 = rL3414;
	}

	public String getRL3415() {
		return this.rL3415;
	}

	public void setRL3415(String rL3415) {
		this.rL3415 = rL3415;
	}

	public String getRL342() {
		return this.rL342;
	}

	public void setRL342(String rL342) {
		this.rL342 = rL342;
	}

	public String getRL343() {
		return this.rL343;
	}

	public void setRL343(String rL343) {
		this.rL343 = rL343;
	}

	public String getRL344() {
		return this.rL344;
	}

	public void setRL344(String rL344) {
		this.rL344 = rL344;
	}

	public String getRL345() {
		return this.rL345;
	}

	public void setRL345(String rL345) {
		this.rL345 = rL345;
	}

	public String getRL346() {
		return this.rL346;
	}

	public void setRL346(String rL346) {
		this.rL346 = rL346;
	}

	public String getRL347() {
		return this.rL347;
	}

	public void setRL347(String rL347) {
		this.rL347 = rL347;
	}

	public String getRL348() {
		return this.rL348;
	}

	public void setRL348(String rL348) {
		this.rL348 = rL348;
	}

	public String getRL349() {
		return this.rL349;
	}

	public void setRL349(String rL349) {
		this.rL349 = rL349;
	}

	public String getRL35() {
		return this.rL35;
	}

	public void setRL35(String rL35) {
		this.rL35 = rL35;
	}

	public String getRL351() {
		return this.rL351;
	}

	public void setRL351(String rL351) {
		this.rL351 = rL351;
	}

	public String getRL3510() {
		return this.rL3510;
	}

	public void setRL3510(String rL3510) {
		this.rL3510 = rL3510;
	}

	public String getRL3511() {
		return this.rL3511;
	}

	public void setRL3511(String rL3511) {
		this.rL3511 = rL3511;
	}

	public String getRL3512() {
		return this.rL3512;
	}

	public void setRL3512(String rL3512) {
		this.rL3512 = rL3512;
	}

	public String getRL3513() {
		return this.rL3513;
	}

	public void setRL3513(String rL3513) {
		this.rL3513 = rL3513;
	}

	public String getRL3514() {
		return this.rL3514;
	}

	public void setRL3514(String rL3514) {
		this.rL3514 = rL3514;
	}

	public String getRL3515() {
		return this.rL3515;
	}

	public void setRL3515(String rL3515) {
		this.rL3515 = rL3515;
	}

	public String getRL352() {
		return this.rL352;
	}

	public void setRL352(String rL352) {
		this.rL352 = rL352;
	}

	public String getRL353() {
		return this.rL353;
	}

	public void setRL353(String rL353) {
		this.rL353 = rL353;
	}

	public String getRL354() {
		return this.rL354;
	}

	public void setRL354(String rL354) {
		this.rL354 = rL354;
	}

	public String getRL355() {
		return this.rL355;
	}

	public void setRL355(String rL355) {
		this.rL355 = rL355;
	}

	public String getRL356() {
		return this.rL356;
	}

	public void setRL356(String rL356) {
		this.rL356 = rL356;
	}

	public String getRL357() {
		return this.rL357;
	}

	public void setRL357(String rL357) {
		this.rL357 = rL357;
	}

	public String getRL358() {
		return this.rL358;
	}

	public void setRL358(String rL358) {
		this.rL358 = rL358;
	}

	public String getRL359() {
		return this.rL359;
	}

	public void setRL359(String rL359) {
		this.rL359 = rL359;
	}

	public String getRL36() {
		return this.rL36;
	}

	public void setRL36(String rL36) {
		this.rL36 = rL36;
	}

	public String getRL37() {
		return this.rL37;
	}

	public void setRL37(String rL37) {
		this.rL37 = rL37;
	}

	public String getRL38() {
		return this.rL38;
	}

	public void setRL38(String rL38) {
		this.rL38 = rL38;
	}

	public String getRL39() {
		return this.rL39;
	}

	public void setRL39(String rL39) {
		this.rL39 = rL39;
	}

	public String getRL41() {
		return this.rL41;
	}

	public void setRL41(String rL41) {
		this.rL41 = rL41;
	}

	public String getRL410() {
		return this.rL410;
	}

	public void setRL410(String rL410) {
		this.rL410 = rL410;
	}

	public String getRL411() {
		return this.rL411;
	}

	public void setRL411(String rL411) {
		this.rL411 = rL411;
	}

	public String getRL412() {
		return this.rL412;
	}

	public void setRL412(String rL412) {
		this.rL412 = rL412;
	}

	public String getRL413() {
		return this.rL413;
	}

	public void setRL413(String rL413) {
		this.rL413 = rL413;
	}

	public String getRL414() {
		return this.rL414;
	}

	public void setRL414(String rL414) {
		this.rL414 = rL414;
	}

	public String getRL415() {
		return this.rL415;
	}

	public void setRL415(String rL415) {
		this.rL415 = rL415;
	}

	public String getRL42() {
		return this.rL42;
	}

	public void setRL42(String rL42) {
		this.rL42 = rL42;
	}

	public String getRL43() {
		return this.rL43;
	}

	public void setRL43(String rL43) {
		this.rL43 = rL43;
	}

	public String getRL44() {
		return this.rL44;
	}

	public void setRL44(String rL44) {
		this.rL44 = rL44;
	}

	public String getRL45() {
		return this.rL45;
	}

	public void setRL45(String rL45) {
		this.rL45 = rL45;
	}

	public String getRL46() {
		return this.rL46;
	}

	public void setRL46(String rL46) {
		this.rL46 = rL46;
	}

	public String getRL47() {
		return this.rL47;
	}

	public void setRL47(String rL47) {
		this.rL47 = rL47;
	}

	public String getRL48() {
		return this.rL48;
	}

	public void setRL48(String rL48) {
		this.rL48 = rL48;
	}

	public String getRL49() {
		return this.rL49;
	}

	public void setRL49(String rL49) {
		this.rL49 = rL49;
	}

	public String getRL51() {
		return this.rL51;
	}

	public void setRL51(String rL51) {
		this.rL51 = rL51;
	}

	public String getRL510() {
		return this.rL510;
	}

	public void setRL510(String rL510) {
		this.rL510 = rL510;
	}

	public String getRL511() {
		return this.rL511;
	}

	public void setRL511(String rL511) {
		this.rL511 = rL511;
	}

	public String getRL512() {
		return this.rL512;
	}

	public void setRL512(String rL512) {
		this.rL512 = rL512;
	}

	public String getRL513() {
		return this.rL513;
	}

	public void setRL513(String rL513) {
		this.rL513 = rL513;
	}

	public String getRL514() {
		return this.rL514;
	}

	public void setRL514(String rL514) {
		this.rL514 = rL514;
	}

	public String getRL515() {
		return this.rL515;
	}

	public void setRL515(String rL515) {
		this.rL515 = rL515;
	}

	public String getRL52() {
		return this.rL52;
	}

	public void setRL52(String rL52) {
		this.rL52 = rL52;
	}

	public String getRL53() {
		return this.rL53;
	}

	public void setRL53(String rL53) {
		this.rL53 = rL53;
	}

	public String getRL54() {
		return this.rL54;
	}

	public void setRL54(String rL54) {
		this.rL54 = rL54;
	}

	public String getRL55() {
		return this.rL55;
	}

	public void setRL55(String rL55) {
		this.rL55 = rL55;
	}

	public String getRL56() {
		return this.rL56;
	}

	public void setRL56(String rL56) {
		this.rL56 = rL56;
	}

	public String getRL57() {
		return this.rL57;
	}

	public void setRL57(String rL57) {
		this.rL57 = rL57;
	}

	public String getRL58() {
		return this.rL58;
	}

	public void setRL58(String rL58) {
		this.rL58 = rL58;
	}

	public String getRL59() {
		return this.rL59;
	}

	public void setRL59(String rL59) {
		this.rL59 = rL59;
	}

	public String getRL61() {
		return this.rL61;
	}

	public void setRL61(String rL61) {
		this.rL61 = rL61;
	}

	public String getRL610() {
		return this.rL610;
	}

	public void setRL610(String rL610) {
		this.rL610 = rL610;
	}

	public String getRL611() {
		return this.rL611;
	}

	public void setRL611(String rL611) {
		this.rL611 = rL611;
	}

	public String getRL612() {
		return this.rL612;
	}

	public void setRL612(String rL612) {
		this.rL612 = rL612;
	}

	public String getRL613() {
		return this.rL613;
	}

	public void setRL613(String rL613) {
		this.rL613 = rL613;
	}

	public String getRL614() {
		return this.rL614;
	}

	public void setRL614(String rL614) {
		this.rL614 = rL614;
	}

	public String getRL615() {
		return this.rL615;
	}

	public void setRL615(String rL615) {
		this.rL615 = rL615;
	}

	public String getRL62() {
		return this.rL62;
	}

	public void setRL62(String rL62) {
		this.rL62 = rL62;
	}

	public String getRL63() {
		return this.rL63;
	}

	public void setRL63(String rL63) {
		this.rL63 = rL63;
	}

	public String getRL64() {
		return this.rL64;
	}

	public void setRL64(String rL64) {
		this.rL64 = rL64;
	}

	public String getRL65() {
		return this.rL65;
	}

	public void setRL65(String rL65) {
		this.rL65 = rL65;
	}

	public String getRL66() {
		return this.rL66;
	}

	public void setRL66(String rL66) {
		this.rL66 = rL66;
	}

	public String getRL67() {
		return this.rL67;
	}

	public void setRL67(String rL67) {
		this.rL67 = rL67;
	}

	public String getRL68() {
		return this.rL68;
	}

	public void setRL68(String rL68) {
		this.rL68 = rL68;
	}

	public String getRL69() {
		return this.rL69;
	}

	public void setRL69(String rL69) {
		this.rL69 = rL69;
	}

	public String getRL71() {
		return this.rL71;
	}

	public void setRL71(String rL71) {
		this.rL71 = rL71;
	}

	public String getRL710() {
		return this.rL710;
	}

	public void setRL710(String rL710) {
		this.rL710 = rL710;
	}

	public String getRL711() {
		return this.rL711;
	}

	public void setRL711(String rL711) {
		this.rL711 = rL711;
	}

	public String getRL712() {
		return this.rL712;
	}

	public void setRL712(String rL712) {
		this.rL712 = rL712;
	}

	public String getRL713() {
		return this.rL713;
	}

	public void setRL713(String rL713) {
		this.rL713 = rL713;
	}

	public String getRL714() {
		return this.rL714;
	}

	public void setRL714(String rL714) {
		this.rL714 = rL714;
	}

	public String getRL715() {
		return this.rL715;
	}

	public void setRL715(String rL715) {
		this.rL715 = rL715;
	}

	public String getRL72() {
		return this.rL72;
	}

	public void setRL72(String rL72) {
		this.rL72 = rL72;
	}

	public String getRL73() {
		return this.rL73;
	}

	public void setRL73(String rL73) {
		this.rL73 = rL73;
	}

	public String getRL74() {
		return this.rL74;
	}

	public void setRL74(String rL74) {
		this.rL74 = rL74;
	}

	public String getRL75() {
		return this.rL75;
	}

	public void setRL75(String rL75) {
		this.rL75 = rL75;
	}

	public String getRL76() {
		return this.rL76;
	}

	public void setRL76(String rL76) {
		this.rL76 = rL76;
	}

	public String getRL77() {
		return this.rL77;
	}

	public void setRL77(String rL77) {
		this.rL77 = rL77;
	}

	public String getRL78() {
		return this.rL78;
	}

	public void setRL78(String rL78) {
		this.rL78 = rL78;
	}

	public String getRL79() {
		return this.rL79;
	}

	public void setRL79(String rL79) {
		this.rL79 = rL79;
	}

	public String getRL81() {
		return this.rL81;
	}

	public void setRL81(String rL81) {
		this.rL81 = rL81;
	}

	public String getRL810() {
		return this.rL810;
	}

	public void setRL810(String rL810) {
		this.rL810 = rL810;
	}

	public String getRL811() {
		return this.rL811;
	}

	public void setRL811(String rL811) {
		this.rL811 = rL811;
	}

	public String getRL812() {
		return this.rL812;
	}

	public void setRL812(String rL812) {
		this.rL812 = rL812;
	}

	public String getRL813() {
		return this.rL813;
	}

	public void setRL813(String rL813) {
		this.rL813 = rL813;
	}

	public String getRL814() {
		return this.rL814;
	}

	public void setRL814(String rL814) {
		this.rL814 = rL814;
	}

	public String getRL815() {
		return this.rL815;
	}

	public void setRL815(String rL815) {
		this.rL815 = rL815;
	}

	public String getRL82() {
		return this.rL82;
	}

	public void setRL82(String rL82) {
		this.rL82 = rL82;
	}

	public String getRL83() {
		return this.rL83;
	}

	public void setRL83(String rL83) {
		this.rL83 = rL83;
	}

	public String getRL84() {
		return this.rL84;
	}

	public void setRL84(String rL84) {
		this.rL84 = rL84;
	}

	public String getRL85() {
		return this.rL85;
	}

	public void setRL85(String rL85) {
		this.rL85 = rL85;
	}

	public String getRL86() {
		return this.rL86;
	}

	public void setRL86(String rL86) {
		this.rL86 = rL86;
	}

	public String getRL87() {
		return this.rL87;
	}

	public void setRL87(String rL87) {
		this.rL87 = rL87;
	}

	public String getRL88() {
		return this.rL88;
	}

	public void setRL88(String rL88) {
		this.rL88 = rL88;
	}

	public String getRL89() {
		return this.rL89;
	}

	public void setRL89(String rL89) {
		this.rL89 = rL89;
	}

	public String getRL91() {
		return this.rL91;
	}

	public void setRL91(String rL91) {
		this.rL91 = rL91;
	}

	public String getRL910() {
		return this.rL910;
	}

	public void setRL910(String rL910) {
		this.rL910 = rL910;
	}

	public String getRL911() {
		return this.rL911;
	}

	public void setRL911(String rL911) {
		this.rL911 = rL911;
	}

	public String getRL912() {
		return this.rL912;
	}

	public void setRL912(String rL912) {
		this.rL912 = rL912;
	}

	public String getRL913() {
		return this.rL913;
	}

	public void setRL913(String rL913) {
		this.rL913 = rL913;
	}

	public String getRL914() {
		return this.rL914;
	}

	public void setRL914(String rL914) {
		this.rL914 = rL914;
	}

	public String getRL915() {
		return this.rL915;
	}

	public void setRL915(String rL915) {
		this.rL915 = rL915;
	}

	public String getRL92() {
		return this.rL92;
	}

	public void setRL92(String rL92) {
		this.rL92 = rL92;
	}

	public String getRL93() {
		return this.rL93;
	}

	public void setRL93(String rL93) {
		this.rL93 = rL93;
	}

	public String getRL94() {
		return this.rL94;
	}

	public void setRL94(String rL94) {
		this.rL94 = rL94;
	}

	public String getRL95() {
		return this.rL95;
	}

	public void setRL95(String rL95) {
		this.rL95 = rL95;
	}

	public String getRL96() {
		return this.rL96;
	}

	public void setRL96(String rL96) {
		this.rL96 = rL96;
	}

	public String getRL97() {
		return this.rL97;
	}

	public void setRL97(String rL97) {
		this.rL97 = rL97;
	}

	public String getRL98() {
		return this.rL98;
	}

	public void setRL98(String rL98) {
		this.rL98 = rL98;
	}

	public String getRL99() {
		return this.rL99;
	}

	public void setRL99(String rL99) {
		this.rL99 = rL99;
	}

	public String getSonographer1() {
		return this.sonographer1;
	}

	public void setSonographer1(String sonographer1) {
		this.sonographer1 = sonographer1;
	}

	public String getSonographer10() {
		return this.sonographer10;
	}

	public void setSonographer10(String sonographer10) {
		this.sonographer10 = sonographer10;
	}

	public String getSonographer11() {
		return this.sonographer11;
	}

	public void setSonographer11(String sonographer11) {
		this.sonographer11 = sonographer11;
	}

	public String getSonographer12() {
		return this.sonographer12;
	}

	public void setSonographer12(String sonographer12) {
		this.sonographer12 = sonographer12;
	}

	public String getSonographer13() {
		return this.sonographer13;
	}

	public void setSonographer13(String sonographer13) {
		this.sonographer13 = sonographer13;
	}

	public String getSonographer14() {
		return this.sonographer14;
	}

	public void setSonographer14(String sonographer14) {
		this.sonographer14 = sonographer14;
	}

	public String getSonographer15() {
		return this.sonographer15;
	}

	public void setSonographer15(String sonographer15) {
		this.sonographer15 = sonographer15;
	}

	public String getSonographer16() {
		return this.sonographer16;
	}

	public void setSonographer16(String sonographer16) {
		this.sonographer16 = sonographer16;
	}

	public String getSonographer17() {
		return this.sonographer17;
	}

	public void setSonographer17(String sonographer17) {
		this.sonographer17 = sonographer17;
	}

	public String getSonographer18() {
		return this.sonographer18;
	}

	public void setSonographer18(String sonographer18) {
		this.sonographer18 = sonographer18;
	}

	public String getSonographer2() {
		return this.sonographer2;
	}

	public void setSonographer2(String sonographer2) {
		this.sonographer2 = sonographer2;
	}

	public String getSonographer3() {
		return this.sonographer3;
	}

	public void setSonographer3(String sonographer3) {
		this.sonographer3 = sonographer3;
	}

	public String getSonographer4() {
		return this.sonographer4;
	}

	public void setSonographer4(String sonographer4) {
		this.sonographer4 = sonographer4;
	}

	public String getSonographer5() {
		return this.sonographer5;
	}

	public void setSonographer5(String sonographer5) {
		this.sonographer5 = sonographer5;
	}

	public String getSonographer6() {
		return this.sonographer6;
	}

	public void setSonographer6(String sonographer6) {
		this.sonographer6 = sonographer6;
	}

	public String getSonographer7() {
		return this.sonographer7;
	}

	public void setSonographer7(String sonographer7) {
		this.sonographer7 = sonographer7;
	}

	public String getSonographer8() {
		return this.sonographer8;
	}

	public void setSonographer8(String sonographer8) {
		this.sonographer8 = sonographer8;
	}

	public String getSonographer9() {
		return this.sonographer9;
	}

	public void setSonographer9(String sonographer9) {
		this.sonographer9 = sonographer9;
	}

	public String getTsh() {
		return this.tsh;
	}

	public void setTsh(String tsh) {
		this.tsh = tsh;
	}
	
	public String getAmh() {
		return amh;
	}

	public void setAmh(String amh) {
		this.amh = amh;
	}

	public String getTxt1() {
		return this.txt1;
	}

	public void setTxt1(String txt1) {
		this.txt1 = txt1;
	}

	public String getTxt2() {
		return this.txt2;
	}

	public void setTxt2(String txt2) {
		this.txt2 = txt2;
	}

//	public List<String> getPatientInfo(int demo){
//		return ivfTrackSheetDao.findInfoByDemographic(demo);		
//	}
}