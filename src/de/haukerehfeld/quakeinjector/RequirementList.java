/*
Copyright 2009 Hauke Rehfeld


This file is part of QuakeInjector.

QuakeInjector is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

QuakeInjector is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with QuakeInjector.  If not, see <http://www.gnu.org/licenses/>.
*/
package de.haukerehfeld.quakeinjector;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import java.util.Comparator;
import java.util.Collections;
import java.lang.Iterable;
import java.util.Iterator;

import javax.swing.event.ChangeListener;

public class RequirementList implements Iterable<Requirement> {

	private HashMap<String, Integer> names = new HashMap<String, Integer>();
	private List<Requirement> requirements;
	
	public RequirementList() {
		this.requirements = Collections.<Requirement>emptyList();
	}
	public RequirementList(List<Requirement> requirements) {
		setRequirements(requirements);
	}

	public void setRequirements(List<Requirement> requirements) {
		names.clear();
		for (int i = 0; i < requirements.size(); ++i) {
			names.put(requirements.get(i).getId(), i);
		}
		this.requirements = requirements;

	}

	public void setInstalled(PackageFileList l) {
		Requirement r = get(l.getId());
		System.out.println("Setting " + l.getId() + " to installed");
		r.setInstalled(true);
		if (r instanceof Package) {
			((Package) r).setFileList(l);
		}
	}

	public void writeInstalled() throws java.io.IOException {
		new InstalledPackageList().write(this);
	}

	public Requirement get(int i) {
		return requirements.get(i);
	}
	
	public Requirement get(String id) {
		Integer i = names.get(id);
		if (i == null) {
			return new UnavailableRequirement(id);
		}
		return requirements.get(i);
	}		

	public Iterator<Requirement> iterator() {
		return requirements.iterator();
	}

	public int size() {
		return requirements.size();
	}

	public int indexOf(Requirement r) {
		return requirements.indexOf(r);
	}


	/**
	 * easily have change listeners
	 */
	private ChangeListenerList listeners = new ChangeListenerList();

	public void addChangeListener(ChangeListener l) {
		listeners.addChangeListener(l);
	}
	public void removeChangeListener(ChangeListener l) {
		listeners.removeChangeListener(l);
	}
	public void notifyChangeListeners() {
		listeners.notifyChangeListeners(this);
	}

}