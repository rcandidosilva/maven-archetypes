package com.project.util.test;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collection;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependency;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolutionFilter;

public class TestUtils {

	public static JavaArchive getArtifacts(String... artifactsId) throws Exception {
		File[] files = getMavenDependency(artifactsId);
		ZipImporter importer = ShrinkWrap.create(ZipImporter.class, "new.jar");
		for (File file : files) {
			importer.importFrom(file);
		}
		return importer.as(JavaArchive.class);
	}

	public static File getPomXml() throws Exception {
		return new File(ClassLoader.getSystemResource(".").getFile() + "/../pom.xml");
	}

	public static File[] getMavenDependencies(final String... artifacts)
			throws Exception {
		return getMavenDependencies(getPomXml(), artifacts);
	}

	public static File[] getMavenDependencies(File pom,
			final String... artifacts) {
		MavenDependencyResolver resolver = DependencyResolvers
				.use(MavenDependencyResolver.class);
		resolver = resolver.includeDependenciesFromPom(pom.getPath());
		return resolver.resolveAsFiles(new MavenResolutionFilter() {
			public MavenResolutionFilter configure(
					Collection<MavenDependency> dependencies) {
				return null;
			}
			public boolean accept(MavenDependency element) {
				for (String artifact : artifacts) {
					if (element.getCoordinates().contains(artifact)) {
						return true;
					}
				}
				return false;
			}
		});
	}
	
	public static File[] getMavenDependency(boolean goOffline, String... artifactsId) throws Exception {
		MavenDependencyResolver resolver = DependencyResolvers
				.use(MavenDependencyResolver.class)
				.loadMetadataFromPom(getPomXml().getPath());
		if (goOffline) {
			resolver = resolver.goOffline();
		}
		return resolver.artifacts(artifactsId).resolveAsFiles();
	}	

	public static File[] getMavenDependency(String... artifactsId) throws Exception {
		MavenDependencyResolver resolver = DependencyResolvers
				.use(MavenDependencyResolver.class)
				.loadMetadataFromPom(getPomXml().getPath());
		return resolver.artifacts(artifactsId).resolveAsFiles();
	}
}
