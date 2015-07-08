using UnityEngine;
using System.Collections;
using System.Collections.Generic;


namespace SecuredType {
	
	public struct XInt
	{
		static int key = 0x55;
		int	m_secured;

		private XInt(int value)
		{
			m_secured = value ^ key;
		}
		
		public static implicit operator XInt(int value)
		{
			return new XInt(value);
		}

		public static implicit operator int(XInt value)
		{
			return value.Value;
		}

		public int Value
		{
			set{ 
				m_secured = value ^ key;
			}
			get{ 
				return m_secured ^ key;
			}
		}

		public override string ToString()
		{
			return Value.ToString ();
		}
	}
}
