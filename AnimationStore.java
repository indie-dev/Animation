package com.Softy.Samples;

//Apache License 2.0 applies to this code only.
//Read open source license at https://www.softy.xyz/open_license.php

import com.Softy.Templates.Creator;
import com.Softy.Templates.Reader;

public class AnimationStore
{
	//AnimatorUtil attaches an animation to an object
	//In this case, it will be a package name
	//The view using this will attach an animation to a Shortcut/App icon
	//It should animate when the user does something to the view
	//This is so there is no lag in the animation
	//That lag would be caused by Android creating a new instance of Animation class
	//Which is not necessary here, as we are attaching the animation
	//To a view, which can later on call the animation
	
	//As a side note, the developer can have the AnimatorUtil class
	//Store the animation on to the device
	//This can be used for practically any application that uses Java / Kotlin.
	//They just need to import and / or modifty both Creator and Reader class
	//From Softy Templates jar previously created.
	
	private int resAnimation; // <-- What the animation is (R.anim.**)
	private String attachedTo; // <-- Name of the package the animation is attached to
	private View attachedView; // <-- Custom view used. PREFERED.
	private Context mAttachedContext;
	public AnimatorUtil attachAnimationTo(Context mContext, int resAnimation, String attachedTo)
	{
		this.mAttachedContext = mContext;
		this.resAnimation = resAnimation;
		this.attachedTo = attachedTo;
	}
	
	public AnimatorUtil attachAnimationTo(Context mContext, int resAnimation, String attachedTo, View customView)
	{
		this.mAttachedContext = mContext;
		this.resAnimation = resAnimation;
		this.attachedTo = attachedTo;
		this.attachedView = customView;
	}
	
	public void detachAnimation()
	{
		//Detach the attached animation
		this.resAnimation = 0;
		this.attachedTo = "";
		this.destroy();
	}
	
	public void destroy()
	{
		if(isAnimationAttached() != false )
		{
			System.out.println("No need. Animation was already detached.");
		}
		System.out.println("Destroyed attached animation");
	}
	
	public boolean isAnimationAttached()
	{
		if(attachedTo != null)
			return true;
		else
			return false;
	}
	

	public void animateAttachedAnimationOnView(View view)
	{
		//Use Animation from Android jar to animate the attached animation
		if( resAnimation == 0 )
			throw new NullPointerException("resAnimation can not be 0");
		if( mContext == null )
			throw new NullPointerException("mContext is not set. How'd you access me without setting that??");
		Animation mAnimation = AnimationUtils.loadAnimation(mContext, resAnimation);
		view.startAnimation(mAnimation);
	}
	
	public void animateAttachedAnimationOnView()
	{
		//Use Animation from Android jar to animate the attached animation
		//They set attachedView
		//If not, we will throw an npe
		if( attachedView == null )
			throw new NullPointerException("attachedView is not set.");
		//I mean, they did set resAnimation, right?
		if( resAnimation == 0 )
			throw new NullPointerException("resAnimation is not set.");
		Animation mAnimation = AnimationUtils.loadAnimation(mContext, resAnimation);
		attachedView.setAnimation(mAnimation);
	}

	public void writeAnimationToFile(String path)
	{
		//Use Creator class to create an XML we will later read from
		//Make sure to store the attachedAnim as Animator._ANIM_ID
		Creator mCreator = new Creator(path+"/"+attachedTo+".anim")
			.init()
			.addExternalNode("Animator._ANIM_ID",Integer.toString(resAnimation))
			.build();
	}
	
	public int readAnimationFromFile(String path)
	{
		//Use Reader class to read from stored xml
		Reader mReader = new Reader(path+"/"+attachedTo+".anim")
			.init()
			.getExternalNode("Animator._ANIM_ID");
		//Use modified class to get external node
		return mReader.getExternalNode();
	}
}
