package org.jagent.asm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ClassPrinter extends ClassVisitor {
	public ClassPrinter() {
		super(Opcodes.ASM4);
	}

	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		System.out.println(name + " extends " + superName + " {");
	}

	public void visitSource(String source, String debug) {
	}

	public void visitOuterClass(String owner, String name, String desc) {
	}

	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		return null;
	}

	public void visitAttribute(Attribute attr) {
	}

	public void visitInnerClass(String name, String outerName,
			String innerName, int access) {
	}

	public FieldVisitor visitField(int access, String name, String desc,
			String signature, Object value) {
		System.out.println(" " + desc + " " + name);
		return null;
	}

	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		System.out.println(" " + name + desc);
		return null;
	}

	public void visitEnd() {
		System.out.println("}");
	}
	
	public static void main(String[] args) throws IOException {
		File clazz = new File("target/classes/org/jagent/asm/hello/HelloAsm.class");
		System.out.println(clazz.getAbsolutePath());
		FileInputStream fis = new FileInputStream(clazz);
		
		ClassPrinter cp = new ClassPrinter();
		ClassReader cr = new ClassReader(fis);
		cr.accept(cp, 0);
	}
}