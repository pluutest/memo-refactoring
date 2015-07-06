package com.memo.studygroup.nohhs.adapter.item;

import android.os.Parcel;
import android.os.Parcelable;

public class MemoVO implements Parcelable {
	private int id;
	private String memo;
	private String regDate;

	public MemoVO() {}

	public MemoVO(String memo, String regDate) {
		this.memo = memo;
		this.regDate = regDate;
	}

	public MemoVO(int id, String memo, String regDate) {
		this.id = id;
		this.memo = memo;
		this.regDate = regDate;
	}

	public int getId() {
		return id;
	}

	public String getMemo() {
		return memo;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	@Override
	public int describeContents() { return 0; }

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.id);
		dest.writeString(this.memo);
		dest.writeString(this.regDate);
	}

	protected MemoVO(Parcel in) {
		this.id = in.readInt();
		this.memo = in.readString();
		this.regDate = in.readString();
	}

	public static final Creator<MemoVO> CREATOR = new Creator<MemoVO>() {
		public MemoVO createFromParcel(Parcel source) {return new MemoVO(source);}

		public MemoVO[] newArray(int size) {return new MemoVO[size];}
	};
}
