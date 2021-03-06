package com.chooloo.www.koler.ui.callactions

import android.view.KeyEvent
import com.chooloo.www.koler.ui.base.BasePresenter
import com.chooloo.www.koler.util.call.CallManager

class CallActionsPresenter<V : CallActionsMvpView> :
    BasePresenter<V>(),
    CallActionsMvpPresenter<V> {

    private var _isMuted = false
    private var _isSpeaker = false
    private var _isRecording = false
    private var _isHolding = false

    override fun onKeypadClick() {
        mvpView?.openDialpad()
    }

    override fun onAddCallClick() {
        mvpView?.addCall()
    }

    override fun onHoldClick() {
        _isHolding = !_isHolding.also {
            CallManager.hold(!it)
        }
    }

    override fun onMuteClick() {
        _isMuted = !_isMuted.also {
            mvpView?.toggleMute(!it)
        }
    }

    override fun onRecordClick() {
        if (_isRecording) {
            mvpView?.stopRecording()
        } else {
            mvpView?.startRecording()
        }
    }

    override fun onSpeakerClick() {
        _isSpeaker = !_isSpeaker.also {
            mvpView?.toggleSpeaker(!it)
        }
    }

    override fun onKeypadKey(keyCode: Int, event: KeyEvent) {
        CallManager.keypad(event.number)
    }
}