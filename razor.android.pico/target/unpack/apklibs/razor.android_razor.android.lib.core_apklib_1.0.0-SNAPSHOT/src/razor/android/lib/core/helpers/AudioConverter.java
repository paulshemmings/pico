package razor.android.lib.core.helpers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class AudioConverter {
	
	public static byte[] convertPCMtoULAW(byte[] input) {
		try {
			ByteArrayInputStream bIn = new ByteArrayInputStream(input);
			ByteArrayOutputStream bOut = new ByteArrayOutputStream();
			
			UlawEncoderInputStream  ulawEncode = new UlawEncoderInputStream(bIn, 0);
			
			byte[] encodeBuf = new byte[1024];
			int read = ulawEncode.read(encodeBuf, 0, 1024);
			while (read != -1) {
				bOut.write(encodeBuf, 0, read);
				read = ulawEncode.read(encodeBuf, 0, 1024);
			}
			
			ByteArrayOutputStream ulawBuffer = new ByteArrayOutputStream();
			WaveHeader waveHeader = new WaveHeader(WaveHeader.FORMAT_ULAW, (short)1, 8000, (short)8, bOut.size());
			waveHeader.write(ulawBuffer);
	
			bOut.writeTo(ulawBuffer);
			
			return ulawBuffer.toByteArray();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
