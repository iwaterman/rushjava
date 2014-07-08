package org.jagent.asm.generate;

import static org.objectweb.asm.Opcodes.*;

import java.io.FileOutputStream;
import java.io.IOException;

import org.objectweb.asm.ClassWriter;

public class CreateMyTask {

	public static void main(String[] args) throws IOException {
		ClassWriter cw = new ClassWriter(0);
		
		// 类的修饰说明
		cw.visit(V1_5, ACC_PUBLIC + ACC_ABSTRACT,
		"org/jagent/asm/generate/MyTask", null, "java/lang/Object",
		new String[] { "java.lang.Runnable" });
		
		// 添加私有属性name
		cw.visitField(ACC_PRIVATE, "tName", "Ljava/lang/String;", null, "Task")
		  .visitEnd();

		cw.visitMethod(ACC_PUBLIC, "run","()V", null, null)
		  .visitEnd();
		
		cw.visitEnd();
		
		byte[] code = cw.toByteArray();
		
        FileOutputStream fos = new FileOutputStream("MyTask.class");
        fos.write(code);
        fos.close();

	}

}
