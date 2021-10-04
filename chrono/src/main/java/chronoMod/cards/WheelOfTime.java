package chronoMod.cards;

import chronoMod.DefaultMod;
import chronoMod.characters.Chronomancer;
import chronoMod.powers.JadePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.FlameBarrierEffect;

import static chronoMod.DefaultMod.makeCardPath;

public class WheelOfTime extends AbstractXCostCard {
    public static final String ID = DefaultMod.makeID(WheelOfTime.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Chronomancer.Enums.COLOR_BLUE;

    private static final int BONUS_DAMAGE = 6;
    private static final int UPGRADE_PLUS_BONUS_DMG = 3;

    public WheelOfTime() {
        super(ID, IMG, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = BONUS_DAMAGE;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect = this.getEffectNum(p);
        if (effect > 0) {
            if (Settings.FAST_MODE) {
                this.addToBot(new VFXAction(p, new FlameBarrierEffect(p.hb.cX, p.hb.cY), 0.1F));
            } else {
                this.addToBot(new VFXAction(p, new FlameBarrierEffect(p.hb.cX, p.hb.cY), 0.5F));
            }
            for (int i = 1; i <= effect; i++) {
                this.addToBot(new ApplyPowerAction(p, p, new VigorPower(p, this.magicNumber), this.magicNumber));
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_BONUS_DMG);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new WheelOfTime();
    }
}
