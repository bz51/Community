package com.community.core;

public class Parameter {
	/**
	 * �������򣺰���_������
	 * PS:
	 * 	RE:resident��
	 * 	MA:manager��
	 */
	//ס����½
	public static final String RE_LOGIN = "resident/loginAction!login";
	//�жϸ��û����Ƿ��ѱ�ע��
	public static final String RE_IS_LOGIN = "resident/loginAction!isLogin";
	//ס��ע��
	public static final String RE_SIGNIN = "resident/loginAction!signin";
	//���ݹؼ��ֲ�ѯС��
	public static final String RE_GETCOMMUNITY = "resident/communityAction!getCommunity";
	//���ݻ���id���Ƹ�ס������Ϣ
	public static final String RE_SETUSER = "resident/userAction!setUser";
	//��ȡ���͸���ǰס����С������
	public static final String RE_GETMYINFO = "resident/communityAction!getMyInfo";
	//��ȡ���͸���ǰס����С�����������
	public static final String RE_GETMYINFOCOUNT = "resident/communityAction!getMyInfoCount";
	//��ȡ��С����̳�б�
	public static final String RE_GETTOPICS = "resident/topicAction!getTopics";
	//��������id��ȡ����������
	public static final String RE_GETTOPICCOMMENTS = "resident/topicAction!getTopicComments";
	//����һ������
	public static final String RE_POSTTOPICCOMMENT = "resident/topicAction!postTopicComment";
	//����һ������
	public static final String RE_POSTTOPICS = "resident/topicAction!postTopic";
	//���ҷ���������
	public static final String RE_GETMYTOPICS = "resident/topicAction!getMyTopics";
	//���ҵ�����+�������漰������
	public static final String RE_GETMYCOMTOPICS = "resident/topicAction!getMyComTopics";
	//��������id��ȡ����
	public static final String RE_GETTOPICBYID = "resident/topicAction!getTopicById";
	//���ݻ���id��ȡ��ס������Ϣ
	public static final String RE_GETUSER = "resident/userAction!getUser";
	//��ҵ��½
	public static final String MA_LOGIN = "manager/loginAction!login";
	//��ҵע��
	public static final String MA_SIGNIN = "manager/loginAction!signin";
	//�жϸ��û����Ƿ��ѱ�ע��
	public static final String MA_ISLOGIN = "manager/loginAction!isLogin";
	//��ȡ��С�����й���
	public static final String MA_POSTINFO = "manager/communityAction!postInfo";
	//��ȡ��С�����й���
	public static final String MA_GETINFOS = "manager/communityAction!getInfos";
	//������ҵ����Աid��ȡ�����Աʵ��
	public static final String MA_GETMANAGERBYID = "manager/communityAction!getManagerById";
	//���������Ϣ����������Ϣ(���ݻ��������ؼ���+¥��+��Ԫ+����+���ƺ�)
	public static final String MA_SEARCHUSER = "manager/communityAction!searchUser";
	//��ȡ��Ҫ��Ȩ���û���Ϣ
	public static final String MA_GETAUTHORS = "manager/authorAction!getAuthors";
	//Ϊĳһ�û���Ȩ
	public static final String MA_AUTHORIZE = "manager/authorAction!authorize";
	//��ȡ����绰
	public static final String RE_GETPHONE = "resident/communityAction!getPhone";
	//����һ������绰
	public static final String MA_POSTPHONE = "manager/communityAction!postPhone";
	
	
	public static final String SEVER_EXCEPTION = "�������˷����쳣��������";
	
	//���ԡ�MaSigninActivity��
	public static final String FROM_MA_SIGNIN_ACTIVITY = "MaSigninActivity";
	
	//������ip
	public static final String SERVER_IP = "115.28.217.42";
	//�ͻ��˶˿ں�
	public static final int CLIENT_PORT = 4567;
	
	//���ص���ϢΪ��
	public static final String NULL = "ľ����Ϣ~";
	
	
	
	
	
	
	
}
